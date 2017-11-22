package com.trainline.testing.objects;

import android.support.annotation.Nullable;

public class Node {
    public final @Nullable Node left;
    public final @Nullable Node right;

    public Node(@Nullable Node left, @Nullable Node right) {
        this.left = left;
        this.right = right;
    }
}
