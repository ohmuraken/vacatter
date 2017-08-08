package com.fernandocejas.android10.sample.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {
    private static final int FAKE_POST_ID = 9;
    private Post post;

    @Before
    public void setUp() {
        post = new Post(FAKE_POST_ID);
    }

    @Test
    public void testPostConstructorHappyCase() {
        final int postId = post.getPostId();

        assertThat(postId).isEqualTo(FAKE_POST_ID);
    }
}
