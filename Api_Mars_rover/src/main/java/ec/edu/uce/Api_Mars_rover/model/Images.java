package ec.edu.uce.Api_Mars_rover.model;

public class Images {

    private String link;

    public Images(String link) {

        StringBuilder sb = new StringBuilder(link);

        if(sb.charAt(4) == 's') {
            this.link = link;
        }else {
            this.link = sb.insert(4, 's').toString();
        }

    }

    public String getLink() {
        return link;
    }
}
