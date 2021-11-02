package academy.mischok.ipcalculatorwebapp;

public class SubnetMask extends IpAddress {

    public SubnetMask(String ipInput) {
        super(ipInput);
    }

    public String getCidr() {

        String resultBinary = this.toBinaryString();

        int count = 0;
        char temp;

        for (int i = 0; i < resultBinary.length(); i++) {
            temp = resultBinary.charAt(i);
            if (temp == '1') {
                count++;
            }
        }
        String cidr = Integer.toString(count);

        return cidr;
    }

    @Override
    public void setFirst(int first) {
        if (first != 255) {
            super.setFirst(first);
        }
    }

    @Override
    public void setSecond(int second) {
        super.setSecond(second);
    }

    @Override
    public void setThird(int third) {
        super.setThird(third);
    }

    @Override
    public void setFourth(int fourth) {
        super.setFourth(fourth);
    }
}