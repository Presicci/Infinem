package io.ruin.api.utils;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TemporaryAttributesHolder {

    /**
     * Stored attributes
     */
    private Map<String, Object> temporaryAttributes;

    /**
     * Destroys attributes
     */
    protected void destroyAttributes() {
        if(temporaryAttributes == null)
            return;
        temporaryAttributes.clear();
        temporaryAttributes = null;
    }

    /**
     * Returns whether the given key is present.
     * @param key The unique key.
     * @return True if given key exists, false if not.
     */
    public boolean hasTemporaryAttribute(String key) {
        return temporaryAttributes != null && temporaryAttributes.containsKey(key);
    }

    public boolean hasTemporaryAttribute(AttributeKey key) {
        return hasTemporaryAttribute(key.name());
    }

    /**
     * Stores the given attribute with the given key.
     * @param key   The unique key.
     * @param value The value to be stored.
     */
    public void putTemporaryAttribute(String key, Object value) {
        if(temporaryAttributes == null)
            temporaryAttributes = Collections.synchronizedMap(new HashMap<>());
        temporaryAttributes.put(key, value);
    }

    public void putTemporaryAttribute(AttributeKey key, Object value) {
        putTemporaryAttribute(key.name(), value);
    }

    /**
     * Removes an attribute stored with the given key.
     * @param key The unique key.
     * @param <T> The class (value) type.
     * @return The removed attribute - null if none.
     */
    public <T> T removeTemporaryAttribute(String key) {
        if(temporaryAttributes == null)
            return null;
        Object value = temporaryAttributes.remove(key);
        return value == null ? null : (T) value;
    }

    public void removeTemporaryAttribute(AttributeKey key) {
        removeTemporaryAttribute(key.name());
    }

    /**
     * Retrieves an attribute stored with the given key.
     * @param key The unique key.
     * @param <T> The class (value) type.
     * @return The retrieved attribute - null if none.
     */
    public <T> T getTemporaryAttribute(String key) {
        if(temporaryAttributes == null)
            return null;
        Object value = temporaryAttributes.get(key);
        return value == null ? null : (T) value;
    }

    public <T> T getTemporaryAttribute(AttributeKey key) {
        return getTemporaryAttribute(key.name());
    }

    /**
     * Retrieves an attribute stored with the given key.
     * If null, returns the given nullValue.
     * @param key The unique key.
     * @param defaultValue The value to return if no attribute is found.
     * @param <T> The class (value) type.
     * @return The retrieved attribute - nullValue if none.
     */
    public <T> T getTemporaryAttributeOrDefault(String key, T defaultValue) {
        T attribute = getTemporaryAttribute(key);
        return attribute == null ? defaultValue : attribute;
    }

    public <T> T getTemporaryAttributeOrDefault(AttributeKey key, T defaultValue) {
        return getTemporaryAttributeOrDefault(key.name(), defaultValue);
    }

    /**
     * Increments amount stored at given key.
     * If null, inputs given amount at given key.
     * @param key The unique key.
     * @param amount The value to increment by.
     * @return The new value stored at the given key.
     */
    public int incrementTemporaryNumericAttribute(String key, int amount) {
        Object object;
        if (temporaryAttributes == null) {
            temporaryAttributes = Collections.synchronizedMap(new HashMap<>());
            object = null;
        } else
            object = temporaryAttributes.get(key);
        if (object != null && !(object instanceof Number)) {
            throw new IllegalArgumentException("Temporary Attribute with key [" + key + "] is not numeric.");
        }
        int newAmount = object == null ? amount : ((Number) object).intValue() + amount;
        temporaryAttributes.put(key, newAmount);
        return newAmount;
    }

    public int incrementTemporaryNumericAttribute(AttributeKey key, int amount) {
        return incrementTemporaryNumericAttribute(key.name(), amount);
    }

    /**
     * Returns either the value stored at given key, or 0.
     * @param key The unique key.
     * @return The integer stored at given key, or 0.
     */
    public int getTemporaryAttributeIntOrZero(String key) {
        if (temporaryAttributes == null)
            return 0;
        Object value = temporaryAttributes.get(key);
        if (value == null) return 0;
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        if (!(value instanceof Number)) return 0;
        return ((Number) value).intValue();
    }

    public int getTemporaryAttributeIntOrZero(AttributeKey key) {
        return getTemporaryAttributeIntOrZero(key.name());
    }

    public String temporaryAttributesString() {
        if (temporaryAttributes == null) return "No temp attributes.";
        StringBuilder sb = new StringBuilder();
        for (String key : temporaryAttributes.keySet()) {
            sb.append("{");
            sb.append(key);
            sb.append(", ");
            sb.append(temporaryAttributes.get(key));
            sb.append("}\n");
        }
        return sb.toString();
    }
}