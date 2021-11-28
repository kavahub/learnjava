package io.github.kavahub.learnjava;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

import lombok.extern.slf4j.Slf4j;

/**
 * JSR-354功能
 * 
 * <p>
 * “货币和金钱”API的目标：
 * 
 * <ul>
 * <li> 提供处理和计算货币金额的API </li>
 * <li> 定义货币和货币金额的类别，以及货币四舍五入 </li>
 * <li> 处理汇率 </li>
 * <li> 处理货币和货币金额的格式化和解析 </li>
 * </ul>
 * 
 * <p>
 * CurrencyUnit: 货币单位
 * <p>
 * MonetaryAmount： 货币金额
 * <p>
 * CurrencyConversion: 货币转换
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class JavaMoneyExample {
    CurrencyUnit USD;
    MonetaryAmount fstAmtUSD;
    MonetaryAmount fstAmtEUR;
    MonetaryAmount oneDolar;
    MonetaryAmount moneyof;
    MonetaryAmount fastmoneyof;
    MonetaryAmount roundEUR;
    MonetaryAmount calcAmtUSD;
    MonetaryAmount[] monetaryAmounts;
    MonetaryAmount sumAmtCHF;
    MonetaryAmount calcMoneyFastMoney;
    MonetaryAmount convertedAmountEURtoUSD;
    MonetaryAmount convertedAmountEURtoUSD2;
    MonetaryAmount convertedAmountUSDtoEUR;
    MonetaryAmount convertedAmountUSDtoEUR2;
    MonetaryAmount multiplyAmount;
    MonetaryAmount divideAmount;
    MonetaryAmount oneDivThree;
    CurrencyConversion convEUR;
    CurrencyConversion convUSD;
    CurrencyConversion conversionUSD;
    CurrencyConversion conversionEUR;
    MonetaryAmount oneEuro;
    MonetaryAmountFormat formatUSD;
    MonetaryAmountFormat customFormat;
    String usFormatted;
    String customFormatted;

    public JavaMoneyExample() {
        USD = Monetary.getCurrency("USD");
        fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(USD).setNumber(200.50).create();
        fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();
        oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        moneyof = Money.of(12, USD);
        fastmoneyof = FastMoney.of(2, USD);

        log.info("First Amount in USD : " + fstAmtUSD);
        log.info("First Amount in EUR : " + fstAmtEUR);
        log.info("One Dolar : " + oneDolar);
        log.info("MoneyOf : " + moneyof);
        log.info("FastMoneyOf : " + fastmoneyof);

        try {
            @SuppressWarnings("unused")
            CurrencyUnit AAA = Monetary.getCurrency("AAA");
        } catch (UnknownCurrencyException e) {
            log.error("Unknown Currency");
        }

        roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());

        log.info("Rounded EUR : " + roundEUR);

        calcAmtUSD = Money.of(1, "USD").subtract(fstAmtUSD);

        log.info("Substracting amounts : " + calcAmtUSD);

        calcMoneyFastMoney = moneyof.subtract(fastmoneyof);

        log.info("Money & FastMoney operations : " + calcMoneyFastMoney);

        monetaryAmounts = new MonetaryAmount[] { Money.of(100, "CHF"), Money.of(10.20, "CHF"), Money.of(1.15, "CHF"), };
        sumAmtCHF = Money.of(0, "CHF");
        for (MonetaryAmount monetaryAmount : monetaryAmounts) {
            sumAmtCHF = sumAmtCHF.add(monetaryAmount);
        }

        log.info("Adding amounts : " + sumAmtCHF);

        multiplyAmount = oneDolar.multiply(0.25);
        log.info("Multiply Amount : " + multiplyAmount);

        divideAmount = oneDolar.divide(0.25);
        log.info("Divide Amount : " + divideAmount);

        try {
            oneDivThree = oneDolar.divide(3);
        } catch (ArithmeticException e) {
            log.error("One divide by Three is an infinite number");
        }

        convEUR = MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency("EUR").build());
        convUSD = MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency(USD).build());

        conversionUSD = MonetaryConversions.getConversion("USD");
        conversionEUR = MonetaryConversions.getConversion("EUR");

        convertedAmountEURtoUSD = fstAmtEUR.with(conversionUSD);
        convertedAmountEURtoUSD2 = fstAmtEUR.with(convUSD);
        convertedAmountUSDtoEUR = oneDolar.with(conversionEUR);
        convertedAmountUSDtoEUR2 = oneDolar.with(convEUR);
        log.info("C1 - " + convertedAmountEURtoUSD);
        log.info("C2 - " + convertedAmountEURtoUSD2);
        log.info("One Euro -> " + convertedAmountUSDtoEUR);
        log.info("One Euro2 -> " + convertedAmountUSDtoEUR2);

        oneEuro = Money.of(1, "EUR");

        if (oneEuro.equals(FastMoney.of(1, "EUR"))) {
            log.info("Money == FastMoney");
        } else {
            log.info("Money != FastMoney");
        }

        if (oneDolar.equals(Money.of(1, "USD"))) {
            log.info("Factory == Money");
        } else {
            log.info("Factory != Money");
        }

        formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        usFormatted = formatUSD.format(oneDolar);
        log.info("One dolar standard formatted : " + usFormatted);

        customFormat = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.NAME).set("pattern", "00000.00 ¤").build());
        customFormatted = customFormat.format(oneDolar);
        log.info("One dolar custom formatted : " + customFormatted);
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        JavaMoneyExample java9Money = new JavaMoneyExample();
    }
}
