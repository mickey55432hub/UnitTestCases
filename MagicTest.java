package com.myproject.sample;

public class MagicTest
{

    @Test
    public void testHelloWorld() {
        assertEquals("hello world", Magic.getHelloWorld());
    }

   
     @Test
    public void testNumber10() {
        assertEquals(10, Magic.getNumber10());
    }
   
}
