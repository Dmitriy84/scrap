package io.tzero.aqa.framework.utils

import de.taimos.totp.TOTP
import org.apache.commons.codec.binary.Base32
import org.apache.commons.codec.binary.Hex

object TotpUtils {
    fun getTOTPCode(secretKey: String?) =
        TOTP.getOTP(Hex.encodeHexString(Base32().decode(secretKey)))
}
