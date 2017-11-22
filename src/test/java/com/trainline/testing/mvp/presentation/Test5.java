package com.trainline.testing.mvp.presentation;

import com.flextrade.jfixture.JFixture;
import com.trainline.testing.TestSchedulersWrapper;
import com.trainline.testing.helpers.R;
import com.trainline.testing.mvp.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Single;
import rx.functions.Func1;

import static org.mockito.Mockito.*;

public class Test5 {

    @Mock View mockView;
    @Mock Interactor mockInteractor;
    @Mock
    Func1<DeliveryMethodDomain, Model> mockMapper;

    private Presenter sut;
    private JFixture fixture;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fixture = new JFixture();
        sut = new Presenter(mockView, mockInteractor, mockMapper, new TestSchedulersWrapper());
    }

    @Test
    public void loadsData_success() {
        //init
        DeliveryMethodDomain fixtDomain = fixture.create(DeliveryMethodDomain.class);
        Model fixtModel = fixture.create(Model.class);

        when(mockInteractor.getDomain()).thenReturn(Single.just(fixtDomain));
        when(mockMapper.call(fixtDomain)).thenReturn(fixtModel);
        //run
        sut.loadData();
        //verify
        verify(mockView).showMostPopular(fixtModel.mostPopularIconVisible);
        verify(mockView).setDeliveryMethodIcon(fixtModel.typeIcon);
        verify(mockView).setPrice(fixtModel.price);
        verify(mockView).showErrorMessage(null);
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void loadsData_interactorFailure() {
        //init
        String fixtError = fixture.create(String.class);
        when(mockInteractor.getDomain()).thenReturn(Single.<DeliveryMethodDomain>error(new Throwable(fixtError)));
        //run
        sut.loadData();
        //verify
        verify(mockView).showMostPopular(false);
        verify(mockView).setDeliveryMethodIcon(R.drawable.ic_delivery_no_icon);
        verify(mockView).setPrice(null);
        verify(mockView).showErrorMessage(fixtError);
        verifyNoMoreInteractions(mockView, mockMapper);
    }

    @Test
    public void loadsData_mapperFailure() {
        //init
        String fixtError = fixture.create(String.class);
        DeliveryMethodDomain fixtDomain = fixture.create(DeliveryMethodDomain.class);

        when(mockInteractor.getDomain()).thenReturn(Single.just(fixtDomain));
        when(mockMapper.call(fixtDomain)).thenThrow(new RuntimeException(fixtError));
        //run
        sut.loadData();
        //verify
        verify(mockMapper).call(fixtDomain);
        verify(mockView).showMostPopular(false);
        verify(mockView).setDeliveryMethodIcon(R.drawable.ic_delivery_no_icon);
        verify(mockView).setPrice(null);
        verify(mockView).showErrorMessage(fixtError);
        verifyNoMoreInteractions(mockView, mockMapper);
    }
}
