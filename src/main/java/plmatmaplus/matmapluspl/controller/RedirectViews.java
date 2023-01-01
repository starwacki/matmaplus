package plmatmaplus.matmapluspl.controller;

 enum RedirectViews {
    WRONG_LOGIN_VIEW("redirect:/matmaplus/login?usererror"),
    SUCCESS_LOGIN_VIEW("redirect:/matmaplus/login?succes"),
    USER_MUST_LOGIN_VIEW("redirect:/matmaplus/login?mustlogin"),
    WRONG_USERNAME_REGISTER_VIEW("redirect:/matmaplus/register?userExist"),
    WRONG_EMAIL_REGISTER_VIEW("redirect:/matmaplus/register?emailTaken"),
    WRONG_PASSWORD_LENGTH_REGISTER_VIEW("redirect:/matmaplus/register?wrongPasswordLength"),
    WRONG_PASSWORD_REGISTER_VIEW("redirect:/matmaplus/register?wrongPassword"),
    SUCCESS_REGISTER_VIEW("redirect:/matmaplus/register?success"),
     CART_VIEW("redirect:/matmaplus/cart"),
    SHOP_VIEW("redirect:/matmaplus/shop"),
    WRONG_PROMO_CODE_CART_VIEW( "redirect:/matmaplus/cart?wrongCode");



    private final String endpoint;
   RedirectViews(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return endpoint;
    }
}
