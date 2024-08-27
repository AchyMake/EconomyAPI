JitPack.io Check Repository = https://jitpack.io/#AchyMake/EssentialsA
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.AchyMake</groupId>
        <artifactId>EconomyAPI</artifactId>
        <version>LATEST</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
```java
public final class MainPlugin extends JavaPlugin {
    private static MainPlugin instance;
    private static EconomyProvider economy = null;
    @Override
    public void onEnable() {
        instance = this;
        var reg = getServer().getServicesManager().getRegistration(EconomyProvider.class);
        if (reg != null) {
            economy = reg.getProvider();
        } else {
            //send log message about economy provider missing
        }
    }
    @Override
    public void onDisable() {
    }
    public EconomyProvider getEconomy() {
        return economy;
    }
    public static MainPlugin getInstance() {
        return instance;
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
}
```