package com.redhat.fuse.boosters.rest.http;

import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import com.redhat.fuse.boosters.rest.http.pojos.Greetings;

@Service("greetingsService")
public class GreetingsServiceImpl implements GreetingsService {

    private static final String THE_GREETINGS = "Hello,  ";
    
    private static final String THE_GOODBYE = " Nice to see you ";

    @Override
    public Greetings getGreetings(@Header("name") String name ) {
        return new Greetings( THE_GREETINGS + name + THE_GOODBYE);
    }

}