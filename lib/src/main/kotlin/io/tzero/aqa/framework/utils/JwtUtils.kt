package io.tzero.aqa.framework.utils

import io.medici.tzero.jwtauth.JwtData
import io.medici.tzero.jwtauth.JwtFactory
import io.medici.tzero.jwtauth.RSAPrivateKeyProvider
import io.tzero.aqa.framework.api.base.BaseTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive
import org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance
import org.bouncycastle.util.io.pem.PemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.StringReader
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.RSAPrivateCrtKeySpec
import java.util.*

@Component
class JwtUtils {
    @Value("\${app.vaultPrivateKeyPath}")
    lateinit var privateKey: String

    @Value("\${app.privateKeyID}")
    lateinit var privateKeyId: String

    fun createUserJwt(data: JwtData, validityInSeconds: Long) =
        JwtFactory(PrivateKeyTest(privateKey, privateKeyId), validityInSeconds).createJwt(data)

    fun parseJsonCSRFToken(accessToken: String) = getJsonValues(accessToken)["jti"]?.jsonPrimitive?.content

    private fun getJsonValues(accessToken: String) =
        BaseTest.json.decodeFromString<Map<String, JsonElement>>(
            Base64.getDecoder()
                .decode(accessToken.split(".")[1])
                .toString(Charsets.UTF_8)
        )

    inner class PrivateKeyTest(
        private val pkcs: String,
        private val keyId: String
    ) : RSAPrivateKeyProvider {
        override fun getPrivateKey() = PemReader(StringReader(javaClass.classLoader.getResource(pkcs).readText()))
            .use { pemReader ->
                getInstance(pemReader.readPemObject().content).let {
                    KeyFactory.getInstance("RSA")
                        .generatePrivate(
                            RSAPrivateCrtKeySpec(
                                it.modulus,
                                it.publicExponent,
                                it.privateExponent,
                                it.prime1,
                                it.prime2,
                                it.exponent1,
                                it.exponent2,
                                it.coefficient
                            )
                        ) as RSAPrivateKey
                }
            }

        override fun getPrivateKeyId(): String = keyId
    }
}
