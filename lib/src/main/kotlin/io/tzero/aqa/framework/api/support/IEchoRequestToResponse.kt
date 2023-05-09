package io.tzero.aqa.accounts.support

interface IEchoRequestToResponse<T> {
    fun echo(source: T): T
}
