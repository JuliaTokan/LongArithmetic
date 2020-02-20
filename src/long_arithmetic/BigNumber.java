package long_arithmetic;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class BigNumber {
    final int signum;

    final int[] num;

    final int length;

    public BigNumber(String val) {
        int len = val.length();

        if (len == 0)
            throw new NumberFormatException("Zero length BigInteger");

        int cursor = 0;
        int sign = 1;
        int index_minus = val.lastIndexOf('-');
        int index_plus = val.lastIndexOf('+');
        if (index_minus >= 0) {
            if (index_minus != 0 || index_plus >= 0) {
                throw new NumberFormatException("Illegal embedded sign character");
            }
            sign = -1;
            cursor = 1;
        } else if (index_plus >= 0) {
            if (index_plus != 0) {
                throw new NumberFormatException("Illegal embedded sign character");
            }
            cursor = 1;
        }
        if (cursor == len)
            throw new NumberFormatException("Zero length BigInteger");

        if (val.charAt(cursor) == '0'&&(len-cursor)==1) {
            signum = 0;
            num = ZERO.num;
            length = 0;
            return;
        }

        int numDigits = len - cursor;
        signum = sign;

        int num_len = numDigits;
        for(int i = cursor; i<numDigits; i++){
            if(val.charAt(i)=='0'){
                cursor++;
                num_len--;
            }
            else break;
        }
        length = num_len;
        num = new int[length];

        for (int i = 0, g = cursor; i < length && g < val.length(); i++, g++) {
            num[i] = Integer.parseInt("" + val.charAt(g));
        }
    }

    public BigNumber add(BigNumber val) {
        if (val.signum == 0)
            return this;
        if (signum == 0)
            return val;

        int[] num_arr_1, num_arr_2;

        if (signum == -1 && val.signum == 1)
            return val.subtract(new BigNumber(num, length, 1));

        if (signum == 1 && val.signum == -1)
            return subtract(new BigNumber(val.num, val.length, 1));

        int sign = 1;
        if (signum == -1 && val.signum == -1)
            sign = -1;

        int len = Math.max(length, val.length) + 1;

        if (length == val.length) {
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, 1, length);
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, 1, val.length);
        } else if (length > val.length) {
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, 1, length);
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, length - val.length + 1, val.length);
        } else {
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, 1, val.length);
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, val.length - length + 1, length);
        }
        int[] res_num = new int[len];
        int g = 0;
        for (int i = len - 1; i >= 0; i--) {
            g = g + num_arr_1[i] + num_arr_2[i];
            int k = g % 10;
            res_num[i] = g % 10;
            g /= 10;
        }
        int i = 0;
        while (res_num[res_num.length - len] == 0 && len > 1) len--;
        return new BigNumber(Arrays.copyOfRange(res_num, res_num.length - len, res_num.length), len, sign);
    }

    public BigNumber subtract(BigNumber val) {
        if (signum == -1 && val.signum == 1)
            return add(new BigNumber(val.num, val.length, -1));

        if (signum == 1 && val.signum == -1)
            return add(new BigNumber(val.num, val.length, 1));

        if (signum == -1 && val.signum == -1){
            return new BigNumber(val.num, val.length, 1).subtract(new BigNumber(num, length, 1));
        }

        int sign = this.compareTo(val) == 1 ? 1 : -1;

        int len = Math.max(length, val.length);
        int[] num_arr_1, num_arr_2;

        if (length == val.length) {
            num_arr_1 = num;
            num_arr_2 = val.num;
        } else if (length > val.length) {
            num_arr_1 = num;
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, length - val.length, val.length);
        } else {
            num_arr_2 = val.num;
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, val.length - length, length);
        }
        int[] a_num;
        if (sign == -1) {
            a_num = Arrays.copyOf(num_arr_1, len);
            num_arr_1 = num_arr_2;
            num_arr_2 = a_num;
        }

        int res = 0;
        int[] res_num = new int[len];
        for (int i = len - 1; i >= 0; i--) {
            if (num_arr_1[i] < num_arr_2[i]) {
                num_arr_1[i] += 10;
                num_arr_1[i - 1]--;
            }
            res = res + num_arr_1[i] - num_arr_2[i];
            res_num[i] = res % 10;
            res /= 10;
        }
        while (res_num[res_num.length - len] == 0 && len > 1) len--;
        if (len == 1 && res_num[res_num.length - 1] == 0)
            return BigNumber.ZERO;
        return new BigNumber(Arrays.copyOfRange(res_num, res_num.length - len, res_num.length), len, sign);
    }

    public BigNumber multiply(BigNumber val) {
        if (length == 0 || val.length == 0)
            return BigNumber.ZERO;
        int sign = 1;
        if (signum == -1 && val.signum == -1)
            sign = 1;
        else if (signum == -1 || val.signum == -1)
            sign = -1;

        int len = length + val.length + 1;
        int[] num_arr_1, num_arr_2;

        if (length == val.length) {
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, len - length, length);
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, len - length, val.length);
        } else if (length > val.length) {
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, len - length, length);
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, len - val.length, val.length);
        } else {
            num_arr_2 = new int[len];
            System.arraycopy(val.num, 0, num_arr_2, len - val.length, val.length);
            num_arr_1 = new int[len];
            System.arraycopy(num, 0, num_arr_1, len - length, length);
        }

        int res = 0;
        int[] res_num = new int[len];

        for (int i = len - 1; i > len - length - 2; i--)
            for (int j = len - 1; j > len - val.length - 2; j--) {
                res = res + num_arr_1[i] * num_arr_2[j] + res_num[i + j - (len - 1)];
                res_num[i + j - (len - 1)] = res % 10;
                res /= 10;
            }

        while (res_num[res_num.length - len] == 0 && len > 1) len--;
        return new BigNumber(Arrays.copyOfRange(res_num, res_num.length - len, res_num.length), len, sign);
    }

    public BigNumber divide(BigNumber val) {
        if (val.length == 0)
            throw new ArithmeticException("Divide by zero");
        if (length == 0)
            return BigNumber.ZERO;

        int sign = 1;
        if (signum == -1 && val.signum == -1)
            sign = 1;
        else if (signum == -1 || val.signum == -1)
            sign = -1;

        int len = length;

        int ost_len = 0;
        int res = 0;
        int[] res_num = new int[len];
        int[] ost_num = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = len - 1 - ost_len; j < len - 1; j++) ost_num[j] = ost_num[j + 1];
            ost_num[len - 1] = num[i];
            ost_len++;
            int cnt = 0;
            BigNumber ost = new BigNumber(Arrays.copyOfRange(ost_num, ost_num.length - ost_len, ost_num.length), ost_len, 1);
            BigNumber b = new BigNumber(val.num, val.length, 1);
            while (ost.geq(b)) {
                //ost = ost.subtract(b);
                ost = ost.sub(b);
                ost_len = ost.length;
                if (ost_len != 0)
                    System.arraycopy(ost.num, 0, ost_num, ost_num.length - ost_len, ost_len);
                cnt++;
            }
            res_num[i] = cnt;
        }

        while (res_num[res_num.length - len] == 0 && len > 1) len--;
        if (len == 1 && res_num[res_num.length - 1] == 0)
            return BigNumber.ZERO;
        return new BigNumber(Arrays.copyOfRange(res_num, res_num.length - len, res_num.length), len, sign);
    }

    /*public BigNumber mod(BigNumber val) {
        BigNumber div = this.divide(val);
        div=div.multiply(val);
        return abs(this).subtract(abs(div));
        }*/

    public BigNumber pow(long n) {
        if (length == 0)
            return BigNumber.ZERO;
        int sign = (int) Math.pow(signum, n);
        BigNumber res = new BigNumber(num, length, signum);
        for (int i = 0; i < n-1; i++) {
            res = res.multiply(this);
        }
        return new BigNumber(res.num, res.length, sign);
    }

    public BigNumber powMod(long n, long mod) {
        if(n==1)
            return new BigNumber(""+this.mod(mod));
        if (length == 0)
            return BigNumber.ZERO;
        int sign = (int) Math.pow(signum, n);

        BigNumber res = new BigNumber(num, length, signum);
        for (long i = 0; i < n-1; i++) {
            res = new BigNumber(""+res.multiply(this).mod(mod));
        }
        return new BigNumber(res.num, res.length, sign);
    }

    public BigNumber powMod(BigNumber n, BigNumber mod) {
        BigInteger num = new BigInteger(this.toString());
        BigInteger step = new BigInteger(n.toString());
        BigInteger m = new BigInteger(mod.toString());

        BigInteger res = num.modPow(step, m);
        return new BigNumber(res.toString());
    }

    public BigNumber sqrt() {
        if (signum == -1)
            throw new ArithmeticException("Negative sqrt");
        int len = (length + 1) / 2;
        int[] res_num = new int[len];
        BigNumber g;
        for (int i = 0; i < len; i++) {
            res_num[i] = 9;
            BigNumber c = new BigNumber(res_num, len, 1);
            g = c.multiply(c);
            while (g.gr(this)) {
                res_num[i]--;
                g = c.multiply(c);
            }
        }
        return new BigNumber(res_num, len, 1);
    }

    public long mod(long mod) {
        int len = length;
        long ost = 0;
        int[] res_num = new int[length];
        for (int i = 0; i < length; i++) {
            ost = ost * 10 + num[i];
            res_num[i] = (int)(ost / mod);
            ost %= mod;
        }
        if (signum == -1 && ost != 0)
            return mod - ost;
        return ost;
    }

    public BigNumber Mod(BigNumber m) {
        BigInteger mod = new BigInteger(m.toString());
        /*long mod = m.num();
        int len = length;
        long ost = 0;
        int[] res_num = new int[length];
        for (int i = 0; i < length; i++) {
            ost = ost * 10 + num[i];
            res_num[i] = (int)(ost / mod);
            ost %= mod;
        }*/
        BigInteger num = new BigInteger(this.toString());
        BigInteger res = num.mod(mod);
        if (res.compareTo(BigInteger.ZERO)<0)
            return new BigNumber(mod.subtract(res).toString());
        return new BigNumber(res.toString());
    }

    public BigNumber ModS(BigNumber m) {
        long mod = m.num();
        int len = length;
        long ost = 0;
        int[] res_num = new int[length];
        for (int i = 0; i < length; i++) {
            ost = ost * 10 + num[i];
            res_num[i] = (int)(ost / mod);
            ost %= mod;
        }
        if (signum == -1 && ost != 0)
            return new BigNumber("-"+ost);
        return new BigNumber(ost+"");
    }

    public BigNumber addMod(BigNumber b, BigNumber m) {
        BigNumber sum = this.add(b);
        return sum.ModS(m);
    }

    public BigNumber sub(BigNumber number){
        BigInteger num2 = new BigInteger(number.toString());
        BigInteger num1 = new BigInteger(this.toString());
        return new BigNumber(num1.subtract(num2).toString());
    }

    public BigNumber mul(BigNumber number){
        BigInteger num2 = new BigInteger(number.toString());
        BigInteger num1 = new BigInteger(this.toString());
        return new BigNumber(num1.multiply(num2).toString());
    }

    public BigNumber divMod(BigNumber b, int mod){
        BigNumber h = modInverse(b, mod);
        return this.multiply(h);
    }

    public static BigNumber modInverse(BigNumber a, long mod) {
        BigNumber m0 = new BigNumber("" + mod);
        BigNumber m = new BigNumber("" + mod);
        BigNumber y = BigNumber.ZERO;
        BigNumber x = BigNumber.ONE;
        if (mod == 1)
            return BigNumber.ZERO;
        while (a.gr(BigNumber.ONE)) {
            BigNumber q = a.divide(m);
            BigNumber t = m;

            m = new BigNumber("" + a.mod(Integer.parseInt(m.toString())) + "");
            a = t;
            t = y;

            y = x.subtract(q.multiply(y));
            x = t;
        }

        if (x.less(BigNumber.ZERO)) {
            x = x.add(m0);
        }
        return x;
    }

    // num1 > num2
    public boolean gr(BigNumber val) {
        int res = this.compareTo(val);
        return res == 1 ? true : false;
    }

    // num1 >= num2
    public boolean geq(BigNumber val) {
        int res = this.compareTo(val);
        return res >= 0 ? true : false;
    }

    // num1 < num2
    public boolean less(BigNumber val) {
        int res = this.compareTo(val);
        return res == -1 ? true : false;
    }

    // num1 <= num2
    public boolean leq(BigNumber val) {
        int res = this.compareTo(val);
        return res <= 0 ? true : false;
    }

    public boolean equals(BigNumber val) {
        int res = this.compareTo(val);
        return res == 0 ? true : false;
    }

    public boolean noEquals(BigNumber val) {
        int res = this.compareTo(val);
        return res != 0 ? true : false;
    }

    public int compareTo(BigNumber val) {
        if (signum == val.signum) {
            switch (signum) {
                case 0:
                    return 0;
                default: {
                    if (length == val.length) {
                        for (int i = 0; i < length; i++) {
                            if (num[i] > val.num[i])
                                return signum * 1;
                            else if (num[i] < val.num[i])
                                return signum * (-1);
                        }
                        return 0;
                    } else
                        return ((length > val.length && signum == 1) || (length < val.length && signum == -1)) ? 1 : -1;
                }
            }
        }
        return signum > val.signum ? 1 : -1;
    }

    public static void calculateSystem() {
        System.out.println("Calculate system : x ≡ b_i (mod m_i)");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a size of system:");
        int size = sc.nextInt();

        BigNumber[] b = new BigNumber[size];
        /*b[0] = new BigNumber("2");
        b[1] = new BigNumber("5");
        b[2] = new BigNumber("5");
        b[3] = new BigNumber("6");*/
        long[] m = new long[size];//{7, 8, 9, 11};
        enterSystem(b,m);

        for(int i =0; i<size; i++){
            System.out.println("x ≡ "+b[i]+"(mod "+m[i]+")");
        }

        long M = 1;
        for(long mi: m)
            M*=mi;

        BigNumber[] Mi = new BigNumber[size];
        for (int i = 0; i < size; i++) {
            Mi[i] = BigNumber.ONE;
            for (int j = 0; j < size; j++) {
                if (i != j)
                    Mi[i] = Mi[i].multiply(new BigNumber("" + m[j]));
            }
        }

        BigNumber[] Ni = new BigNumber[size];
        for (int i = 0; i < size; i++) {
            Ni[i] = BigNumber.modInverse(Mi[i], m[i]);
        }

        BigNumber x = BigNumber.ZERO;
        for (int i = 0; i < size; i++) {
            x = x.add(b[i].multiply(Ni[i].multiply(Mi[i])));
        }
        System.out.println("\nx = "+x.mod(M));
    }

    private static void enterSystem(BigNumber[] b, long[] m){
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i<b.length; i++){
            System.out.println("Enter a b"+i+":");
            long num = sc.nextLong();
            b[i] = new BigNumber(""+num);
            System.out.println("Enter a m"+i+":");
            m[i] = sc.nextLong();
        }
    }

    public static final BigNumber ZERO = new BigNumber(new int[0], 0, 0);

    public static final BigNumber ONE = new BigNumber(new int[]{1}, 1, 1);

    public static final BigNumber TWO = new BigNumber(new int[]{2}, 1, 1);

    public BigNumber(int[] num, int len, int signum) {
        boolean flag = false;
        for (int i : num)
            if (i != 0) flag = true;
        if (flag) {
            this.num = num;
            this.length = len;
            this.signum = signum;
        } else {
            this.num = new int[0];
            this.length = 0;
            this.signum = 0;
        }
    }

    public String toString() {
        if (length == 0) return "0";
        StringBuilder num_str = new StringBuilder();
        if (signum == -1)
            num_str.append('-');
        for (int i = 0; i < length; i++)
            num_str.append(num[i]);
        return num_str.toString();
    }

    public static BigNumber valueOf(long val) {
        return new BigNumber(""+val);
    }

    public static BigNumber gcd(BigNumber a, BigNumber b)
    {
        if (a.equals(BigNumber.ZERO))
            return b;
        if (b.equals(BigNumber.ZERO))
            return a;
        return gcd(b.Mod(a), a);
    }

    public static BigNumber abs(BigNumber num){
        int sign = num.signum;
        if(sign==-1)
            sign=1;
        return new BigNumber(num.num, num.length, sign);
    }

    public long num(){
        return Long.parseLong(this.toString());
    }

    public int[] getNum(){
        return num;
    }

    public BigNumber correct(){
        for(int i =0; i< num.length; i++){
            if(num[i]>=10){
                break;
            }
        }
        return new BigNumber(num, num.length, signum);
    }
}
