package com.trainline.testing.mvp;

import android.support.annotation.NonNull;
import com.trainline.testing.helpers.R;
import com.trainline.testing.helpers.SchedulersWrapper;
import rx.SingleSubscriber;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class Presenter {
    private final @NonNull View view;
    private final @NonNull Interactor interactor;
    private final @NonNull Func1<DeliveryMethodDomain, Model> modelMapper;
    private final @NonNull SchedulersWrapper schedulersWrapper;

    private final @NonNull CompositeSubscription subscription = new CompositeSubscription();

    public Presenter(@NonNull View view,
                     @NonNull Interactor interactor,
                     @NonNull Func1<DeliveryMethodDomain, Model> modelMapper,
                     @NonNull SchedulersWrapper schedulersWrapper) {
        this.view = view;
        this.interactor = interactor;
        this.modelMapper = modelMapper;
        this.schedulersWrapper = schedulersWrapper;
    }

    public void loadData() {
        subscription.add(
                interactor.getDomain()
                        .map(modelMapper)
                        .subscribeOn(schedulersWrapper.background())
                        .observeOn(schedulersWrapper.main())
                        .subscribe(new SingleSubscriber<Model>() {
                            @Override
                            public void onSuccess(Model model) {
                                bindModel(model);
                            }

                            @Override
                            public void onError(Throwable error) {
                                bindError(error.getMessage());
                            }
                        })
        );
    }

    public void stopLoading() {
        subscription.clear();
    }

    private void bindModel(@NonNull Model model) {
        view.showMostPopular(model.mostPopularIconVisible);
        view.setDeliveryMethodIcon(model.typeIcon);
        view.setPrice(model.price);
        view.showErrorMessage(null);
    }

    private void bindError(@NonNull String errorMessage) {
        view.showMostPopular(false);
        view.setDeliveryMethodIcon(R.drawable.ic_delivery_no_icon);
        view.setPrice(null);
        view.showErrorMessage(errorMessage);
    }
}
