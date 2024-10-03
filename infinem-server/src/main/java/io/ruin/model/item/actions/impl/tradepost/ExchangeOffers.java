package io.ruin.model.item.actions.impl.tradepost;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.tradepost.offer.TradePostOffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/1/2024
 */
public class ExchangeOffers {

    @Expose protected final List<TradePostOffer> OFFERS = new ArrayList<>();

    public static ExchangeOffers INSTANCE = new ExchangeOffers();

    private static final Gson GSON_LOADER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static final String PATH = "../data/ge/ge_offers.json";

    public List<TradePostOffer> fetchOffers(Player player) {
        List<TradePostOffer> offers = OFFERS.stream().filter(offer -> offer.getUsername().equalsIgnoreCase(player.getName())).collect(Collectors.toList());
        List<TradePostOffer> completedOffers = new ArrayList<>();
        AtomicInteger sales = new AtomicInteger();
        offers.forEach(offer -> {
            int soldAmt = offer.unclaimedSales;
            if (soldAmt <= 0) return;
            player.getTradePost().addToCoffer(offer.unclaimedSales * offer.getPricePerItem());
            sales.getAndIncrement();
            if (offer.getItem().getAmount() > 0) {
                player.sendMessage("<col=00c203>" + "Grand Exchange: " + soldAmt + " of " + offer.getItem().getDef().name + " have sold, " + offer.getItem().getAmount() + " remaining.</col>");
                offer.unclaimedSales = 0;
            } else {
                player.sendMessage("<col=00c203>" + "Grand Exchange: Finished selling all of " + offer.getItem().getDef().name + ".</col>");
                completedOffers.add(offer);
            }
            int totalPrice = offer.getPricePerItem() * soldAmt;
            player.getTradePost().updateTradeHistory(new ExchangeHistory(offer.getItem().getId(), soldAmt, totalPrice, ExchangeType.SELLING));
            PlayerCounter.GE_SALES.increment(player, 1);
            if (totalPrice > 1000)
                PlayerCounter.GE_PROFIT.increment(player, totalPrice / 1000);
        });
        if (!completedOffers.isEmpty()) {
            offers.removeAll(completedOffers);
            OFFERS.removeAll(completedOffers);
        }
        if (sales.get() > 0) {
            player.sendMessage("Current Coffer: " + TradePost.formatPrice(player.getTradePost().getCofferAmount()));
        }
        return offers;
    }

    public void updatePrice(TradePostOffer o, int newPrice) {
        int index = OFFERS.indexOf(o);
        OFFERS.set(index, new TradePostOffer(
                o.getUsername(),
                o.getItem(),
                newPrice,
                o.getTimestamp()
        ));
        save();
    }

    public void sold(TradePostOffer o, int sold, boolean online) {
        int index = OFFERS.indexOf(o);
        TradePostOffer offer = new TradePostOffer(
                o.getUsername(),
                new Item(o.getItem().getId(), o.getItem().getAmount() - sold),
                o.getPricePerItem(),
                o.getTimestamp()
        );
        offer.unclaimedSales = o.unclaimedSales + sold;
        OFFERS.set(index, offer);
        save();
    }

    public void removeOffer(TradePostOffer offer) {
        OFFERS.remove(offer);
        save();
    }

    public void addOffer(TradePostOffer offer) {
        OFFERS.add(offer);
        save();
    }

    public void save() {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(INSTANCE);
            File file = new File(PATH);
            Files.write(file.toPath(), json.getBytes());
        } catch (IOException e) {
            System.err.println("Couldn't save grand exchange data. " + e.getMessage());
        }
    }

    static {
        try {
            File saveFile = new File(PATH);
            if (saveFile.exists()) {
                String json = new String(Files.readAllBytes(saveFile.toPath()));
                INSTANCE = GSON_LOADER.fromJson(json, ExchangeOffers.class);
                System.out.println("Loaded grand exchange listings");
            }
        } catch (IOException e) {
            System.err.println("Couldn't load persistent listings. " + e.getMessage());
        }
        // Save every 5 minutes
        World.startEvent(e -> {
            while (true) {
                e.delay(5000); // 5 minutes
                INSTANCE.save();
            }
        });
    }
}
