package com.trainline.testing;

import android.support.annotation.Nullable;
import com.trainline.testing.helpers.SchedulersWrapper;
import com.trainline.testing.mvp.*;

import java.net.URI;

public class Main {
    public static void main(String[] arg) {
        Presenter presenter = new Presenter(
                new TestView(),
                new Interactor(),
                new ModelMapper(new Formatter()),
                new SchedulersWrapper());

        presenter.loadData();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        presenter.stopLoading();
    }

    private static class TestView implements View {

        public void showErrorMessage(@Nullable String errorMessage) {
            System.out.println("Error=" + errorMessage);
        }

        public void showMostPopular(boolean show) {
            System.out.println("MostPopular visible=" + show);
        }

        @Override
        public void setDeliveryMethodIcon(int iconId) {
                System.out.println("icon=" + iconId);
        }

        @Override
        public void setPrice(@Nullable String price) {
            System.out.println("price=" + price);
        }
    }
}
