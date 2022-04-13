
package com.redhat.fuse.boosters.rest.http.pojos;

/**
 * Greetings entity
 *
 */
public class Greetings {

    private String greetings;

    public Greetings(String greetings) {
		super();
		this.greetings = greetings;
	}

	public Greetings() {
    }

	public String getGreetings() {
		return greetings;
	}

	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}

    
}