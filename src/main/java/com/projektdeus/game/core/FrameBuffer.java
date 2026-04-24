package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
    private int fboId, textureId, depthId;
    public int width, height;

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        setup();
    }

    private void setup() {
        fboId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);

        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);

        depthId = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthId);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthId);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind() { glBindFramebuffer(GL_FRAMEBUFFER, fboId); }
    public void unbind() { glBindFramebuffer(GL_FRAMEBUFFER, 0); }

    public void rescale(int w, int h) {
        if (w == width && h == height) return;
        glDeleteFramebuffers(fboId);
        glDeleteTextures(textureId);
        glDeleteRenderbuffers(depthId);
        this.width = w; this.height = h;
        setup();
    }

    public int getTextureId() { return textureId; }
}