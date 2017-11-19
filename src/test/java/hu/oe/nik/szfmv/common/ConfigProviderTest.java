package hu.oe.nik.szfmv.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigProviderTest {

    @Test
    public void provideTest() {
        assertEquals(true, ConfigProvider.provide().getBoolean("sample.toml"));
    }
}
