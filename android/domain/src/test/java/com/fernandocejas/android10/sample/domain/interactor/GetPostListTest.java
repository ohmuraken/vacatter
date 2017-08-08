package com.fernandocejas.android10.sample.domain.interactor;


import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.PostRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetPostListTest {
    private GetPostList getPostList;

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private PostRepository mockPostRepository;

    @Before
    public void setUp() {
        getPostList = new GetPostList(mockPostRepository,
                mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetPostListUseCaseObservableHappyCase() {
        getPostList.buildUseCaseObservable(null);

        verify(mockPostRepository).posts();
        verifyNoMoreInteractions(mockPostRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}
