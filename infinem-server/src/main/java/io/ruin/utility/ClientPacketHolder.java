package io.ruin.utility;

import io.ruin.network.ClientPacket;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ClientPacketHolder {
    ClientPacket[] packets();
}