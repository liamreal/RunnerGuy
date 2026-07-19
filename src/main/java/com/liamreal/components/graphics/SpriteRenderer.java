package com.liamreal.components.graphics;

import com.liamreal.ecs.Component;

public class SpriteRenderer implements Component {
    private SpriteSheet spriteSheet;
    public SpriteRenderer(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
}
