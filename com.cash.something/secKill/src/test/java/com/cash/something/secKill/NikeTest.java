package com.cash.something.secKill;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * author cash
 * create 2018-07-05-13:45
 **/

public class NikeTest {
    @Test
    public void test() throws InterruptedException, IOException {

        String webUrl = "https://www.nike.com/launch/";
        String cartUrl = "https://secure-store.nike.com/us/checkout/html/cart.jsp";

        System.setProperty("webdriver.chrome.driver", "D:\\ideal-ws\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置为 headless 模式 （必须）
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        //设置浏览器窗口打开大小  （非必须）
        chromeOptions.addArguments("--window-size=1120,680");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(webUrl);
        Thread.sleep(10000L);
        snapshot((TakesScreenshot) driver, "homePage.png");

        WebElement link = driver.findElement(By.className("join-log-in"));
        link.click();
        Thread.sleep(20000L);
        snapshot((TakesScreenshot) driver, "modal.png");

        WebElement emailInput = driver.findElement(By.name("emailAddress"));
        emailInput.clear();
        emailInput.sendKeys("442620332@qq.com");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.clear();
        passwordInput.sendKeys("Nik3nike");
        snapshot((TakesScreenshot) driver, "filledModal.png");

        WebElement loginButton = driver.findElement(By.xpath("//input[@type='button']"));
        loginButton.click();
        System.out.println("Login clicked ... ");
        Thread.sleep(30000);
        snapshot((TakesScreenshot) driver, "inLogin.png");

        Set<Cookie> cookieSet = driver.manage().getCookies();
        CookieStore cookieStore = new BasicCookieStore();
        //JSONObject cookieJson=new JSONObject();
        for (Cookie c : cookieSet) {
            System.out.println(JSONObject.toJSON(c));
            //cookieJson.put(c.getName(),c.getValue());
            BasicClientCookie cookie = new BasicClientCookie(c.getName(), c.getName());
            cookie.setDomain(c.getDomain());
            cookie.setPath(c.getPath());
            cookieStore.addCookie(cookie);
        }


        /*CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)//设置Cookie
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();*/

        final String HTTP = "http";
        final String HTTPS = "https";
        SSLConnectionSocketFactory sslsf = null;
        PoolingHttpClientConnectionManager cm = null;
        SSLContextBuilder builder;

        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection
        } catch (Exception e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setDefaultCookieStore(cookieStore)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .build();

        String addCartUrl = "https://secure-store.nike.com/us/services/jcartService/?action=addItem&rt=json&country=US" +
                "&region=na&lang_locale=en_US&catalogId=1&productId=12389587&qty=1&skuId=22112527";

        HttpGet httpGet = new HttpGet(addCartUrl);
        httpGet.setHeader("accept", "*/*");
        httpGet.setHeader("pragma", "no-cache");
        httpGet.setHeader("cache-control", "no-cache");
        httpGet.setHeader("accept-language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)Chrome/64.0.3282.140 Safari/537.36");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        //httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResp = httpclient.execute(httpGet);
        try {
            System.out.println("status:" + httpResp.getStatusLine());
            System.out.println("headers:");
            HeaderIterator iterator = httpResp.headerIterator();
            while (iterator.hasNext()) {
                System.out.println("\t" + iterator.next());
            }

            String responseHtml = EntityUtils.toString(httpResp.getEntity());
            System.out.println("response:" + responseHtml);
        } catch (Exception e) {

        } finally {
            httpResp.close();
        }

    }


    public void snapshot(TakesScreenshot drivername, String filename) {

        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        try {
            //System.out.println("save snapshot path is:E:/"+filename);
            FileUtils.copyFile(scrFile, new File("./snapshot/" + filename));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Can't save screenshot");
            e.printStackTrace();
        } finally {
            //System.out.println("screen shot finished");
        }
    }
}
