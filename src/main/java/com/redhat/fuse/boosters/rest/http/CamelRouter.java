package com.redhat.fuse.boosters.rest.http;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.redhat.fuse.boosters.rest.http.pojos.*;

/**
 * A simple Camel REST DSL route that implements the greetings service.
 * 
 */
@Component
public class CamelRouter extends RouteBuilder {

	@Value("${sms.service.url}")
	String SMS_SERVICE_URL;
    @Override
    public void configure() throws Exception {

        // @formatter:off
        restConfiguration()
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Greeting REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiProperty("base.path", "fund-service/")
                .apiProperty("api.path", "/")
                .apiProperty("host", "")
                .apiContextRouteId("doc-api")
            .component("servlet")
            .bindingMode(RestBindingMode.json);
        
        rest("/greetings").description("Greeting to {name}")
            .get("/{name}").outType(Greetings.class)
                .route().routeId("greeting-api")
                .to("direct:greetingsImpl");
        
        rest("/transfer").description("Transfer operation")
        .post().type(FundRequest.class)
            .route().routeId("fundtransfer-api")
            .to("direct:fundTransferImpl");
        
        
        from("direct:greetingsImpl").description("Greetings REST service implementation route")
        .streamCaching()
        .to("bean:greetingsService?method=getGreetings");
        
        from("direct:fundTransferImpl").description("FundTransfer REST service implementation route")
        .streamCaching()
        .log("New incoming request")
        	.choice()
            	.when().simple("${body.requestType} == 'Debit'")
                	.log("Debit transaction ... new change ... new change ... latest change")
                	.to("direct:debit")		
                .when().simple("${body.requestType} == 'Credit'")
                	.log("Credit transaction")	
                .when().simple("${body.requestType} == 'Rev'")
                	.log("Rev transaction")
                .when().simple("${body.requestType} == 'Block'")
                	.log("Block transaction")	
                .otherwise()
                	.log("Not supported operation")
                	.setHeader(Exchange.HTTP_RESPONSE_CODE).constant(402)
                	.setBody(simple("Operation not supported"));
        
        from("direct:debit")
        	.description("FundTransfer REST service implementation route")
        	.log("${body}")
        	.choice()
        		.when().simple("${body.accountType} == 'Card'")
        			.log("Checking card # and card status")
        			.log("Send SMS")
        			.setBody().simple("")
        			//.setHeader(Exchange.HTTP_URL).simple(SMS_SERVICE_URL)
        			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
        			.to(SMS_SERVICE_URL+"?bridgeEndpoint=true")
        			.convertBodyTo(String.class)
        			.log("${body}")
        		.when().simple("${body.accountType} == 'Account'")
        			.log("Checking card # and card status")
        			.log("Send SMS")
        		.when().simple("${body.accountType} == 'Wallet'")
					.log("Wallet is not supported")
			        .setHeader(Exchange.HTTP_RESPONSE_CODE).constant(402)
			    	.setBody(simple("Wallet not supported"));
        
        // @formatter:on Credit, Rev, Block/Unblock
    }

}
