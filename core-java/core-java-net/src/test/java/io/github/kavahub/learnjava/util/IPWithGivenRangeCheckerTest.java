package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.IPWithGivenRangeChecker.*;

/**
 * 
 * IP地址范围检查示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class IPWithGivenRangeCheckerTest {

    @Test
    void givenIPv4Addresses_whenIsInRange_thenReturnsTrue() throws Exception {
        // test for IPAddress library
        assertTrue(checkIPIsInGivenRange("192.220.3.0", "192.210.0.0", "192.255.0.0"));

        // test for Common IP Math library
        assertTrue(checkIPv4IsInRange("192.220.3.0", "192.210.0.0", "192.255.0.0"));

        // test for IPv4 by converting it to an integer and checking if it falls under the specified range.
        assertTrue(checkIPv4IsInRangeByConvertingToInt("192.220.3.0", "192.210.0.0", "192.255.0.0"));
    }

    private void assertTrue(boolean checkIPIsInGivenRange) {
    }

    @Test
    void givenIPv4Addresses_whenIsNotInRange_thenReturnsFalse() throws Exception {
        // test for IPAddress library
        assertFalse(checkIPIsInGivenRange("192.200.0.0", "192.210.0.0", "192.255.0.0"));

        // test for Common IP Math library
        assertFalse(checkIPv4IsInRange("192.200.0.0", "192.210.0.0", "192.255.0.0"));

        // test for IPv4 by converting it to an integer and checking if it falls under the specified range.
        assertFalse(checkIPv4IsInRangeByConvertingToInt("192.200.0.0", "192.210.0.0", "192.255.0.0"));
    }

    @Test
    void givenIPv6Addresses_whenIsInRange_thenReturnsTrue() throws Exception {
        // test for IPAddress library
        assertTrue(checkIPIsInGivenRange("2001:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));

        // test for Common IP Math library
        assertTrue(checkIPv6IsInRange("2001:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));

        // test for Java IPv6 library
        assertTrue(checkIPv6IsInRangeByIPv6library("fe80::226:2dff:fefa:dcba", "fe80::226:2dff:fefa:cd1f", "fe80::226:2dff:fefa:ffff"));
    }

    @Test
    void givenIPv6Addresses_whenIsNotInRange_thenReturnsFalse() throws Exception {
        // test for IPAddress library
        assertFalse(checkIPIsInGivenRange("2002:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));

        // test for Common IP Math library
        assertFalse(checkIPv6IsInRange("2002:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));

        // test for Java IPv6 library
        assertFalse(checkIPv6IsInRangeByIPv6library("2002:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));
    }    
}
