import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang3.time.StopWatch;

public class dynamicProgramming {

	public static void main(String[] args) {
		
//		Fibonacci with dp
		
		int number;
		BigInteger fibo;
		StopWatch timer;
		
		number = 40;
		timer = StopWatch.createStarted();
		fibo = fib(number);
		timer.stop();
		System.out.println("Fibonacci at:" + number + " is:" + fibo + " Time taken is:" + timer);
		
//		Took 2 seconds with long but 10s with BigInteger		
//		number = 40;
//		timer = StopWatch.createStarted();
//		fibo = fib(number);
//		timer.stop();
//		System.out.println("Fibonacci at:" + number + " is:" + fibo + " Time taken is:" + timer);
		
// 		Takes 30 seconds with long
		
//		number = 50;
//		timer = StopWatch.createStarted();
//		fibo = fib(number);
//		timer.stop();
//		System.out.println("Fibonacci at:" + number + " is:" + fibo + " Time taken is:" + timer);

// 		Takes 5 minutes	with long	
//		number = 55;
//		timer = StopWatch.createStarted();
//		fibo = fib(number);
//		timer.stop();
//		System.out.println("Fibonacci at:" + number + " is:" + fibo + " Time taken is:" + timer);
		
		number = 1000;
		BigInteger[] fibArr = new BigInteger[number + 1];
		Arrays.fill(fibArr, BigInteger.valueOf(-1));	
		fibArr[0] = BigInteger.ZERO;
		fibArr[1] = BigInteger.ONE;
		timer = StopWatch.createStarted();
		dpFib(number, fibArr);
		timer.stop();
		System.out.println("Fibonacci at:" + number + " is:" + fibArr[number] + " Time taken is:" + timer);
	}
	
	static BigInteger dpFib(int n, BigInteger[] arr) {
		BigInteger n1 = arr[n-1];
		BigInteger n2 = arr[n-2];
		if(n1 == BigInteger.valueOf(-1)) {
			n1 = dpFib(n - 1, arr);
		}
		if(n2 == BigInteger.valueOf(-1)) {
			n2 = dpFib(n - 2, arr);
		}
		if(arr[n] == BigInteger.valueOf(-1)) {
			arr[n] = n1.add(n2);
		}
		return arr[n];
	}
	
	static BigInteger fib(int n) {
		if(n == 0) {
			return BigInteger.valueOf(0);
		}
		if(n == 1 || n == 2) {
			return BigInteger.valueOf(1);
		}
		else {
			return fib(n-1).add(fib(n-2));
		}
	}

}
