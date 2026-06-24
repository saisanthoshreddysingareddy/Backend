Restaurant Management System - Requirements

Design class diagram for a Restaurant Management System

  - A restaurant has a name, an address, cuisines served, menu, waiters, chefs.
  - Cuisines can be Italian, French, Chinese, Japanese, Indian, Continental etc.
  - A menu contains a list of dishes divided into multiple sections like Soups, Appetizers etc.
  - A dish has a name, description, type (veg / non-veg / vegan), price, a recipe for the chefs to follow.
  - A dish also has a list of ingredients and its amount required to cook the dish.
  - Ingredients can be measured either in grams for solids or ml (mililitres) for liquids/semiliquids.
  - A waiter has a name, email, phone number and level (Junior, Senior, Manager).
  - A chef has a name, email, phone number and level (Commis Chef, Sous chef, Head Chef, Executive chef).
  - We need to track orders made by customers during their visit so that we can generate a bill at the end.
  - Note: customer during a visit will order multiple times and each time an order can contain multiple dishes in it.
