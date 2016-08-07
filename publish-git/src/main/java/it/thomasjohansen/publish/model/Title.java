package it.thomasjohansen.publish.model;

public class Title {

    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private Title title = new Title();

        public Builder name(String name) {
            title.name = name;
            return this;
        }

        public Title build() {
            try {
                return title;
            } finally {
                title = null;
            }
        }

    }
}
