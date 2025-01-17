package cn.hutool.crypto.digest;

import cn.hutool.core.codec.Base32;
import cn.hutool.crypto.digest.otp.HOTP;
import cn.hutool.crypto.digest.otp.TOTP;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

/**
 * author: xlgogo@outlook.com
 * date: 2021-07-01 18:14
 */
public class OTPTest {

	@Test
	public void genKeyTest() {
		String key = TOTP.generateSecretKey(8);
		assertEquals(8, Base32.decode(key).length);
	}

	@Test
	public void validTest() {
		String key = "VYCFSW2QZ3WZO";
		// 2021/7/1下午6:29:54 显示code为 106659
		//assertEquals(new TOTP(Base32.decode(key)).generate(Instant.ofEpochSecond(1625135394L)),106659);
		TOTP totp = new TOTP(Base32.decode(key));
		Instant instant = Instant.ofEpochSecond(1625135394L);
		assertTrue(totp.validate(instant, 0, 106659));
		assertTrue(totp.validate(instant.plusSeconds(30), 1, 106659));
		assertTrue(totp.validate(instant.plusSeconds(60), 2, 106659));

		assertFalse(totp.validate(instant.plusSeconds(60), 1, 106659));
		assertFalse(totp.validate(instant.plusSeconds(90), 2, 106659));
	}

	@Test
	public void googleAuthTest() {
		String str = TOTP.generateGoogleSecretKey("xl7@qq.com", 10);
		assertTrue(str.startsWith("otpauth://totp/xl7@qq.com?secret="));
	}

	@Test
	public void longPasswordLengthTest() {
		assertThrows(IllegalArgumentException.class, () -> new HOTP(9, "123".getBytes()));
	}

	@Test
	public void generateHOPTTest(){
		byte[] key = "12345678901234567890".getBytes();
		final HOTP hotp = new HOTP(key);
		assertEquals(755224, hotp.generate(0));
		assertEquals(287082, hotp.generate(1));
		assertEquals(359152, hotp.generate(2));
		assertEquals(969429, hotp.generate(3));
		assertEquals(338314, hotp.generate(4));
		assertEquals(254676, hotp.generate(5));
		assertEquals(287922, hotp.generate(6));
		assertEquals(162583, hotp.generate(7));
		assertEquals(399871, hotp.generate(8));
		assertEquals(520489, hotp.generate(9));
	}

	@Test
	public void getTimeStepTest() {
		final Duration timeStep = Duration.ofSeconds(97);

		final TOTP totp = new TOTP(timeStep, "123".getBytes());

		assertEquals(timeStep, totp.getTimeStep());
	}

	@Test
	public void generateHmacSHA1TOPTTest(){
		HmacAlgorithm algorithm = HmacAlgorithm.HmacSHA1;
		byte[] key = "12345678901234567890".getBytes();
		TOTP totp = new TOTP(Duration.ofSeconds(30), 8, algorithm, key);

		int generate = totp.generate(Instant.ofEpochSecond(59L));
		assertEquals(94287082, generate);
		generate = totp.generate(Instant.ofEpochSecond(1111111109L));
		assertEquals(7081804, generate);
		generate = totp.generate(Instant.ofEpochSecond(1111111111L));
		assertEquals(14050471, generate);
		generate = totp.generate(Instant.ofEpochSecond(1234567890L));
		assertEquals(89005924, generate);
		generate = totp.generate(Instant.ofEpochSecond(2000000000L));
		assertEquals(69279037, generate);
		generate = totp.generate(Instant.ofEpochSecond(20000000000L));
		assertEquals(65353130, generate);
	}

	@Test
	public void generateHmacSHA256TOPTTest(){
		HmacAlgorithm algorithm = HmacAlgorithm.HmacSHA256;
		byte[] key = "12345678901234567890123456789012".getBytes();
		TOTP totp = new TOTP(Duration.ofSeconds(30), 8, algorithm, key);

		int generate = totp.generate(Instant.ofEpochSecond(59L));
		assertEquals(46119246, generate);
		generate = totp.generate(Instant.ofEpochSecond(1111111109L));
		assertEquals(68084774, generate);
		generate = totp.generate(Instant.ofEpochSecond(1111111111L));
		assertEquals(67062674, generate);
		generate = totp.generate(Instant.ofEpochSecond(1234567890L));
		assertEquals(91819424, generate);
		generate = totp.generate(Instant.ofEpochSecond(2000000000L));
		assertEquals(90698825, generate);
		generate = totp.generate(Instant.ofEpochSecond(20000000000L));
		assertEquals(77737706, generate);
	}

	@Test
	public void generateHmacSHA512TOPTTest(){
		HmacAlgorithm algorithm = HmacAlgorithm.HmacSHA512;
		byte[] key = "1234567890123456789012345678901234567890123456789012345678901234".getBytes();
		TOTP totp = new TOTP(Duration.ofSeconds(30), 8, algorithm, key);

		int generate = totp.generate(Instant.ofEpochSecond(59L));
		assertEquals(90693936, generate);
		generate = totp.generate(Instant.ofEpochSecond(1111111109L));
		assertEquals(25091201, generate);
		generate = totp.generate(Instant.ofEpochSecond(1111111111L));
		assertEquals(99943326, generate);
		generate = totp.generate(Instant.ofEpochSecond(1234567890L));
		assertEquals(93441116, generate);
		generate = totp.generate(Instant.ofEpochSecond(2000000000L));
		assertEquals(38618901, generate);
		generate = totp.generate(Instant.ofEpochSecond(20000000000L));
		assertEquals(47863826, generate);
	}
}
