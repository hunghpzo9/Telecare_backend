package com.example.telecare.agora.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
