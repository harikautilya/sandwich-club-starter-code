package com.udacity.sandwichclub.model;

import java.util.List;

public class Name {
    private String mainName;
    private List<String> alsoKnownAs = null;

    public Name() {
    }

    public Name(String mainName, List<String> alsoKnownAs) {
        this.mainName = mainName;
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }
}
