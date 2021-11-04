package academy.mischok.ipcalculatorwebapp.domain;

import academy.mischok.ipcalculatorwebapp.domain.IpAddress;

public class SubnetMask extends IpAddress {

    public SubnetMask(String ipInput) {
        super(ipInput);
    }
    public SubnetMask(int ipInput) {
        cidrToSnm(ipInput);
    }



    public int writeCidr() {
        String resultBinary = this.toBinaryString();

        int count = 0;
        char temp;

        for (int i = 0; i < resultBinary.length(); i++) {
            temp = resultBinary.charAt(i);
            if (temp == '1') {
                count++;
            }
        }
        return count;
    }

    public IpAddress invert() {
        int first = ~this.getFirst() & 255; // cut off leading zeros
        int second = ~this.getSecond() & 255; // cut off leading zeros
        int third = ~this.getThird() & 255; // cut off leading zeros
        int fourth = ~this.getFourth() & 255; // cut off leading zeros

        return new IpAddress(first, second, third, fourth);
    }

    public static double numberOfClients(int cidr) {
        int rest = 32 - cidr;

        return (Math.pow(2, rest)) - 2;
    }

    @Override
    public void setFirst(int first) {
        if (first < 0 || first > 255 && !checkSnm(first)) {
            throw new IllegalArgumentException("first byte is out of bounds!");
        }
        super.setFirst(first);
    }

    @Override
    public void setSecond(int second) {
        if (second < 0 || second > 255 && !checkSnm(second) || second != 0 && getFirst() < 255) {
            throw new IllegalArgumentException("second byte is out of bounds!");
        }

        super.setSecond(second);
    }

    @Override
    public void setThird(int third) {
        if (third < 0 || third > 255 && !checkSnm(third) || third != 0 && getSecond() < 255) {
            throw new IllegalArgumentException("third byte is out of bounds!");
        }
        super.setThird(third);
    }

    @Override
    public void setFourth(int fourth) {
        if (fourth < 0 || fourth > 255 && !checkSnm(fourth) || fourth != 0 && getThird() < 255) {
            throw new IllegalArgumentException("fourth byte is out of bounds!");
        }
        super.setFourth(fourth);
    }

    private boolean checkSnm(int octet) {

        int result = 0;
        for (int i = 0; i < 8; i++) {
            result += Math.pow(2, i);
            if ((255-result) == octet) {
                return true;
            }
        }
        return false;
    }

    private String cidrToSnm(int cidr){
        if (cidr < 0 || cidr > 32){
            throw new IllegalArgumentException("CIDR is out of bounds!");
        }
        String cidrString = "";

        switch (cidr / 8){
            case 4:
                cidrString = "255.255.255.255";
                break;
            case 3:
                cidrString = "255.255.255." + calcCidrOctet(cidr) ;
                break;
            case 2:
                cidrString = "255.255." + calcCidrOctet(cidr) + ".0";
                break;
            case 1:
                cidrString = "255." + calcCidrOctet(cidr) + ".0.0";
                break;
            case 0:
                cidrString = calcCidrOctet(cidr) + ".0.0.0";
                break;
        }
        return cidrString;
    }

    private int calcCidrOctet(int cidr){
        int rest = cidr % 8;
        return (int)(256 - Math.pow(2, (8-rest)));
    }
}