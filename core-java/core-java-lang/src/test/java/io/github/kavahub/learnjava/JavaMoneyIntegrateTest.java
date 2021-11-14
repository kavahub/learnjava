package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class JavaMoneyIntegrateTest {
    @Test
    public void givenCurrencyCode_whenString_thanExist() {
        CurrencyUnit usd = Monetary.getCurrency("USD");

        assertNotNull(usd);
        assertEquals(usd.getCurrencyCode(), "USD");
        assertEquals(usd.getNumericCode(), 840);
        assertEquals(usd.getDefaultFractionDigits(), 2);
    }

    @Test
    public void givenCurrencyCode_whenNoExist_thanThrowsError() {
        assertThrows(UnknownCurrencyException.class, () -> Monetary.getCurrency("AAA"));
    }

    @Test
    public void givenAmounts_whenStringified_thanEquals() {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(usd).setNumber(200).create();
        Money moneyof = Money.of(12, usd);
        FastMoney fastmoneyof = FastMoney.of(2, usd);

        assertEquals("USD", usd.toString());
        assertEquals("USD 200.00", fstAmtUSD.toString());
        assertEquals("USD 12.00", moneyof.toString());
        assertEquals("USD 2.00", fastmoneyof.toString());
    }

    @SuppressWarnings("all")
    @Test
    public void givenCurrencies_whenCompared_thanNotequal() {
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        Money oneEuro = Money.of(1, "EUR");

        // Money与FastMoney没有继承关系
        assertFalse(oneEuro.equals(FastMoney.of(1, "EUR")));
        assertTrue(oneDolar.equals(Money.of(1, "USD")));
    }

    @Test
    public void givenAmount_whenDivided_thanThrowsException() {
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        assertEquals("USD 0.33", oneDolar.divide(3).toString());
    }

    @Test
    public void givenAmounts_whenSummed_thanCorrect() {
        List<MonetaryAmount> monetaryAmounts = Arrays.asList(Money.of(100, "CHF"), Money.of(10.20, "CHF"),
                Money.of(1.15, "CHF"));

        Money sumAmtCHF = (Money) monetaryAmounts.stream().reduce(Money.of(0, "CHF"), MonetaryAmount::add);

        assertEquals("CHF 111.35", sumAmtCHF.toString());
    }

    @Test
    public void givenArithmetic_whenStringified_thanEqualsAmount() {
        CurrencyUnit usd = Monetary.getCurrency("USD");

        Money moneyof = Money.of(12, usd);
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(usd).setNumber(200.50).create();
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        Money subtractedAmount = Money.of(1, "USD").subtract(fstAmtUSD);
        MonetaryAmount multiplyAmount = oneDolar.multiply(0.25);
        MonetaryAmount divideAmount = oneDolar.divide(0.25);

        assertEquals("USD", usd.toString());
        assertEquals("USD 1.00", oneDolar.toString());
        assertEquals("USD 200.50", fstAmtUSD.toString());
        assertEquals("USD 12.00", moneyof.toString());
        assertEquals("USD -199.50", subtractedAmount.toString());
        assertEquals("USD 0.25", multiplyAmount.toString());
        assertEquals("USD 4.00", divideAmount.toString());
    }

    @Test
    public void givenAmount_whenRounded_thanEquals() {
        MonetaryAmount fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();
        MonetaryAmount roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());
        assertEquals("EUR 1.30", fstAmtEUR.toString());
        assertEquals("EUR 1.30", roundEUR.toString());
    }

    @Test
    @Disabled("Currency providers are not always available")
    public void givenAmount_whenConversion_thenNotNull() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();

        CurrencyConversion conversionEUR = MonetaryConversions.getConversion("EUR");

        MonetaryAmount convertedAmountUSDtoEUR = oneDollar.with(conversionEUR);

        assertEquals("USD 1.00", oneDollar.toString());
        assertNotNull(convertedAmountUSDtoEUR);
    }

    @Test
    public void givenLocale_whenFormatted_thanEquals() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        MonetaryAmountFormat formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        String usFormatted = formatUSD.format(oneDollar);

        assertEquals("USD 1.00", oneDollar.toString());
        assertNotNull(formatUSD);
        assertEquals("USD1.00", usFormatted);
    }

    @Test
    public void givenAmount_whenCustomFormat_thanEquals() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US)
                .set(CurrencyStyle.NAME).set("pattern", "00000.00 US Dollar").build());
        String customFormatted = customFormat.format(oneDollar);

        assertNotNull(customFormat);
        assertEquals("USD 1.00", oneDollar.toString());
        assertEquals("00001.00 US Dollar", customFormatted);
    }
}
