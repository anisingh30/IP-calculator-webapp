package academy.mischok.ipcalculatorwebapp.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IpAddress {

    private int first;
    private int second;
    private int third;
    private int fourth;

    public IpAddress() {}

    public IpAddress(String address) {
        ipInput(address);
    }
    public IpAddress(int first, int second, int third, int fourth) {
        this.setFirst(first);
        this.setSecond(second);
        this.setThird(third);
        this.setFourth(fourth);
    }
    private void ipInput (String input){
        Objects.requireNonNull(input);
        List<String> ipList = Arrays.asList(input.split("\\."));

        // converts a list of strings into a list of integer
        List<Integer> result = ipList.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        setFirst(result.get(0));
        setSecond(result.get(1));
        setThird(result.get(2));
        setFourth(result.get(3));
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        if (first < 0 || first > 255) {
            throw new IllegalArgumentException("first byte is out of bounds!");
        }
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        if (second < 0 || second > 255) {
            throw new IllegalArgumentException("second byte is out of bounds!");
        }
        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        if (third < 0 || third > 255) {
            throw new IllegalArgumentException("third byte is out of bounds!");
        }
        this.third = third;
    }

    public int getFourth() {
        return fourth;
    }

    public void setFourth(int fourth) {
        if (fourth < 0 || fourth > 255) {
            throw new IllegalArgumentException("fourth byte is out of bounds!");
        }
        this.fourth = fourth;
    }

    @Override
    public String toString() {
        return this.first + "." + this.second + "." + this.third + "." + this.fourth;
    }

    public String toBinaryString() {
        String result = ipComponentToBinaryByte(this.first) + ".";
        result += ipComponentToBinaryByte(this.second) + ".";
        result += ipComponentToBinaryByte(this.third) + ".";
        result += ipComponentToBinaryByte(this.fourth);

        return result;
    }

    private String ipComponentToBinaryByte(int component) {
        String componentBin = Integer.toBinaryString(component);

        while (componentBin.length() < 8) {
            componentBin ="0" + componentBin;
        }

        return componentBin;
    }
    public IpAddress logicalAnd(IpAddress another) {
        Objects.requireNonNull(another);

        int first = this.getFirst() & another.getFirst();
        int second = this.getSecond() & another.getSecond();
        int third = this.getThird() & another.getThird();
        int fourth = this.getFourth() & another.getFourth();

        IpAddress result = new IpAddress();
        result.setFirst(first);
        result.setSecond(second);
        result.setThird(third);
        result.setFourth(fourth);

        return result;
    }

}