package aahl.apiconsumerapp.models;

public class PageInfo {

    private int count;
    private int pages;
    private String next;
    private String prev;

    public int getCount() {
        return count;
    }

    public int getPages() {
        return pages;
    }

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }

    public boolean hasNextPage() {
        return next != null && !next.isEmpty();
    }

    public Integer getNextPageNumber() {
        if (!hasNextPage()) {
            return null;
        }
        try {
            String[] parts = next.split("page=");
            if (parts.length > 1) {
                return Integer.parseInt(parts[1].split("&")[0]);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}

