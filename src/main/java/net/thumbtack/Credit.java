package net.thumbtack;

public class Credit {
    private long id;
    private double sum;
    private boolean isClosed;
    private boolean isOverdue;
    private long borrower;

    public Credit(long id, double sum, boolean isClosed, boolean isOverdue, long borrower) {
        this.id = id;
        this.sum = sum;
        this.isClosed = isClosed;
        this.isOverdue = isOverdue;
        this.borrower = borrower;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public void setBorrower(long borrower) {
        this.borrower = borrower;
    }

    public long getId() {
        return id;
    }

    public double getSum() {
        return sum;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public long getBorrower() {
        return borrower;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", sum=" + sum +
                ", isClosed=" + isClosed +
                ", isOverdue=" + isOverdue +
                ", borrower=" + borrower +
                '}';
    }
}
