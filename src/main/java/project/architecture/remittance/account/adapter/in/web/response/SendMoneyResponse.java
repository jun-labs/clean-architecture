package project.architecture.remittance.account.adapter.in.web.response;

public class SendMoneyResponse {

    private final boolean success;

    private SendMoneyResponse(boolean success) {
        this.success = success;
    }

    public static SendMoneyResponse of(boolean success) {
        return new SendMoneyResponse(success);
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return String.format("Success: %s", success);
    }
}
