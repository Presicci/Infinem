package io.ruin.model.shop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.stat.StatRequirement;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@ToString(callSuper = true)
@JsonPropertyOrder({"id", "amount", "price", "additionalItems", "placeHolderId", "placeholderRule", "requirementCheckType", "requiredLevels"})
@JsonIgnoreProperties({"onBuy", "additionalRequirements", "container", "lootBroadcast", "def", "defaultStockItem"})
@Slf4j
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class ShopItem extends Item {

    public ShopItem() {
        this(0, 0, 0);
    }

    public ShopItem(int id, int amount, int price) {
        this(id, amount, price, null);
    }

    public ShopItem(int id) {
        super(id);
    }

    public ShopItem(int id, int amount, int price, Map<String, String> attributes) {
        super(id, amount, attributes);
        this.price = price;
    }

    public ShopItem(int id, int amount, int price, int buyPrice) {
        this(id, amount, price, buyPrice, null);
    }

    public ShopItem(int id, int amount, int price, int buyPrice, Map<String, String> attributes) {
        super(id, amount, attributes);
        this.price = price;
        this.buyPrice = buyPrice;
    }

    @Override
    public ShopItem copy() {
        ShopItem shopItem = new ShopItem(getId(), getAmount(), getPrice(), copyOfAttributes());

        shopItem.placeholderRule = placeholderRule;
        shopItem.price = this.price;
        shopItem.buyPrice = this.buyPrice;
        shopItem.defaultStockItem = this.defaultStockItem;
        shopItem.placeholderId = this.placeholderId;
        shopItem.requiredLevels = this.requiredLevels;
        shopItem.additionalItems = this.additionalItems;
        shopItem.additionalRequirements = this.additionalRequirements;
        shopItem.onBuy = this.onBuy;
        shopItem.requirementCheckType = this.requirementCheckType;

        return shopItem;
    }

    @Override
    public void remove() {
        if (!defaultStockItem) {
            super.remove();
        }
    }

    @Builder
    public ShopItem(int id, int amount, boolean defaultStockItem, Map<String, String> attributes, PlaceHolderRule placeHolderRule, int price, int placeholderId, List<StatRequirement> requiredLevels, List<Item> additionalItems, Function<Player, String> additionalRequirements, Consumer<Player> onBuy, RequirementCheckType requirementCheckType) {
        super(id, amount, attributes);
        this.defaultStockItem = defaultStockItem;
        this.placeholderRule = placeHolderRule != null ? placeHolderRule : PlaceHolderRule.SHOW_ON_EMPTY;
        this.price = price;
//        if(price == 0){
//            log.warn("ShopItem {} {} has no price!", id, amount);
//        }
        this.placeholderId = placeholderId;
        this.requiredLevels = requiredLevels;
        this.additionalItems = additionalItems;
        this.additionalRequirements = additionalRequirements;
        this.onBuy = onBuy;
        this.requirementCheckType = requirementCheckType != null ? requirementCheckType : RequirementCheckType.NONE;
    }

    public int getDisplayId(Player player) {
        if (placeholderRule != null) {

            int placeHolder = placeholderId == -1 ? getDef().placeholderMainId : placeholderId;
            switch (placeholderRule) {
                case SHOW_ON_EMPTY:
                    return getAmount() == 0 ? placeHolder : super.getId();
                case SHOW_ON_REQUIREMENT_MISSING:
                    return !hasRequirements(player) ? placeHolder : super.getId();
                case SHOW_ON_REQUIREMENT_MET:
                    return hasRequirements(player) ? placeHolder : super.getId();
                default:
                case NONE:
                    break;
            }

        }
        return super.getId();
    }

    public PlaceHolderRule placeholderRule = PlaceHolderRule.SHOW_ON_EMPTY;
    public int price;
    public int buyPrice = -1;
    public boolean defaultStockItem;
    public int placeholderId = -1;
    public List<StatRequirement> requiredLevels;
    public List<Item> additionalItems;
    public Function<Player, String> additionalRequirements;
    public Consumer<Player> onBuy;

    public RequirementCheckType requirementCheckType = RequirementCheckType.NONE;

    public boolean hasRequirements(Player player) {
        boolean hasStats = requiredLevels == null || requiredLevels.isEmpty() || requiredLevels.stream().allMatch(requirement -> requirement.hasRequirement(player));
        boolean meetsAdditionalRequirements = additionalRequirements == null || additionalRequirements.apply(player).isEmpty();
        return hasStats && meetsAdditionalRequirements;
    }

    public boolean printRequirements(Player player) {
        boolean hasStats = requiredLevels == null || requiredLevels.isEmpty() || requiredLevels.stream().allMatch(requirement -> requirement.hasRequirement(player));
        boolean meetsAdditionalRequirements = additionalRequirements == null || additionalRequirements.apply(player).isEmpty();

        if (!hasStats) {
            requiredLevels
                    .stream()
                    .filter(requirement -> !requirement.hasRequirement(player))
                    .forEach(requirement -> player.sendMessage("You require level " + requirement.requiredLevel + " " + requirement.statType.descriptiveName + " to buy this"));
        }
        if (!meetsAdditionalRequirements) {
            player.sendMessage(additionalRequirements.apply(player));
        }
        return hasStats && meetsAdditionalRequirements;
    }

    public void validate() {
        if (placeholderId != -1 && placeholderRule == PlaceHolderRule.NONE) {
            log.warn("Item {} has placeholder ID set but no rule, so will be ignored!", this);
        }
    }
}
