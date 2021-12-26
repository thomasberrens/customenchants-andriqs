package tokeee.rubixstudio.customenchants.builder;

import java.util.List;

public interface Builder {

    LoreBuilder withEnchantments();
    List<String> buildLore();
    List<String> getLore();
}
