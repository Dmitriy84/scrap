package dmalohlovets.playwright.framework

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import com.microsoft.playwright.options.SelectOption
import com.microsoft.playwright.options.WaitForSelectorState
import java.util.stream.Collectors
import kotlin.test.fail

/**
 * O uso das funções da PageBase são para tratar erros na aplicação, que nem sempre a primeira vez não responde.
 * Não necessário o uso de page.click para chamar as funções somente usar os nomes delas diretamente.
 */

open class BaseStep(private val page: Page) {
    var pathProject = System.getProperty("user.dir")

    // opcao que somente verifica se o elemento esta presente na DOM.
    val stateATTACHED: Page.WaitForSelectorOptions =
        Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED)

    // opcao que somente verifica se o elemento esta visivel na DOM.
    val stateVISIBLE: Page.WaitForSelectorOptions = Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE)

    // opcao que somente verifica se o elemento Não esta presente na DOM.
    val stateDETACHED: Page.WaitForSelectorOptions =
        Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED)

    /**
     * A funcao loadPage espera a página carregar por completo.
     "LOAD" - wait for the load event to be fired.
     "DOMCONTENTLOADED" - wait for the DOMContentLoaded event to be fired.
     "NETWORKIDLE" - wait until there are no network connections for at least
     */
    fun loadPage() {
        page.waitForLoadState(LoadState.LOAD)
        page.waitForLoadState(LoadState.DOMCONTENTLOADED)
        page.waitForLoadState(LoadState.NETWORKIDLE)
    }

    /**
     * Pega os testes em uma lista de web elementos.
     * @property locator css or xpath
     */
    fun getListText(locator: String): MutableList<String>? {
        return page.querySelectorAll(locator)
            .stream()
            .map { obj: ElementHandle -> obj.innerText() }
            .collect(Collectors.toList())
    }

    /**
     * click usando o setForce(true), se o elemento existir vai dar um click.
     * @property webElemento css or xpath.
     * @property retry true or false, default is false.
     */
    fun click(
        webElemento: String,
        retry: Boolean = true,
    ) {
        try {
            page.waitForSelector(webElemento, stateATTACHED)
            page.click(webElemento, Page.ClickOptions().setForce(true))
        } catch (ex: Exception) {
            println(ex.message)
            sleep()
            scrollIntoView(webElemento)
            if (retry) page.click(webElemento, Page.ClickOptions().setForce(true)) else fail(ex.message)
        }
        loadPage()
    }

    // espera 5s somente na segunda tentativa.
    fun sleep(value: Long = 5) {
        val sleepValue = value * 1000
        Thread.sleep(sleepValue)
    }

    /**
     * clickAll dar o click em um conjunto de elementos.
     * @property webElemento css or xpath.
     */
    fun clickAll(webElemento: String) {
        page.waitForSelector(webElemento, stateATTACHED)
        scrollIntoView(webElemento)
        val elements = page.querySelectorAll(webElemento)
        for (elem in elements) {
            elem.click()
            loadPage()
        }
    }

    /**
     * isEnabled verifica se o elemento esta presente ou não.
     * @property webElement css or xpath.
     */
    fun isEnabled(webElement: String): Boolean {
        try {
            loadPage()
            val element = page.querySelector(webElement)
            println("Elemento Presente: ${element.isEnabled}, locator: $webElement")
            return element.isEnabled
        } catch (ex: Exception) {
            println("Element is not visible!!!")
            println("Elemento Presente: false")
            return false
        }
    }

    /**
     * isVisible verifica se o elemento esta presente ou não.
     * @property webElement css or xpath.
     */
    fun isVisible(webElement: String): Boolean {
        try {
            loadPage()
            val element = page.querySelector(webElement)
            println("Elemento Visivel: ${element.isVisible}, locator: $webElement")
            return element.isVisible
        } catch (ex: Exception) {
            println(ex.message)
            return false
        }
    }

    /**
     * clickRetry usando o setForce(true), tenta realizar o click 3 vezes.
     * Observação: somente usar quando o botão na tela atual deve sumir.
     * @property webElement css or xpath.
     */
    fun clickRetry(webElement: String): Boolean {
        for (x in 0..3) {
            try {
                if (!isEnabled(webElement)) {
                    break
                }
                page.click(webElement, Page.ClickOptions().setForce(true).setTimeout(15000.0))
                sleep()
            } catch (ex: Exception) {
                println(ex.message)
                println("Falhou ao localizar o elemento: $webElement")
                break
            }
            scrollIntoView(webElement)
        }
        return false
    }

//    /**
//     * clickRetry usando o setForce(true), tenta realizar o click 3 vezes.
//     * Observação: somente usar quando o botão na tela atual deve sumir.
//     * @property webElement css or xpath.
//     */
//    fun loadingPage(): Boolean {
//        var loading = false
//        for (x in 0..3){
//            try {
//                sleep() // espera ele aparecer
//                loading = isVisible(loadElementPage)
//                if(!loading){ break }
//                page.waitForSelector(loadElementPage, stateDETACHED)
//            } catch (ex: Exception){
//                println("Falhou ao localizar o elemento: $loadElementPage")
//            }
//            scrollIntoView(loadElementPage)
//        }
//        if(loading) {
//            fail("Loading preso na tela: $loadElementPage")
//        }
//        return false
//    }

    /**
     * fill usando retry igual a true, espera o elemento esta na Attached e dar o focus no elemento.
     * @property webElemento css or xpath.
     * @property retry true or false, default is false.
     */
    fun fill(
        webElemento: String,
        value: String,
        retry: Boolean = true,
    ) {
        try {
            waitForSelector(webElemento, stateATTACHED)
            page.focus(webElemento)
            page.fill(webElemento, value)
        } catch (ex: Exception) {
            sleep()
            scrollIntoView(webElemento)
            if (retry) page.fill(webElemento, value) else fail(ex.message)
        }
    }

    /**
     * selectByValue usando retry igual a true, seleciona o atributo Value em um combobox.
     * em caso de retry dar um sleep de 5s
     * @property webElement css or xpath.
     * @property retry true or false, default is false.
     */
    fun selectByValue(
        webElement: String,
        value: String,
        retry: Boolean = true,
    ) {
        try {
            waitForSelector(webElement, stateATTACHED)
            page.press(webElement, "Enter")
            page.selectOption(webElement, SelectOption().setValue(value))
            println("Value selecionar: $value")
        } catch (ex: Exception) {
            println("Error na primeira tentativa de selecionar o combobox: $webElement")
            sleep()
            dblclick(webElement)
            if (retry) page.selectOption(webElement, SelectOption().setValue(value)) else fail(ex.message)
        }
    }

    /**
     * selectByLabel usando retry igual a true, seleciona o Label em um combobox, o label eh o texto do conteudo.
     * em caso de retry dar um sleep de 5s
     * @property webElement css or xpath.
     * @property retry true or false, default is false.
     */
    fun selectByLabel(
        webElement: String,
        value: String,
        retry: Boolean = true,
    ) {
        try {
            waitForSelector(webElement)
            page.press(webElement, "Enter")
            page.selectOption(webElement, SelectOption().setLabel(value))
            println("Label selecionar: $value")
            loadPage()
        } catch (ex: Exception) {
            println("Error na primeira tentativa de selecionar o combobox: $webElement")
            sleep() // espera 5s para segunda tentativa
            dblclick(webElement)
            if (retry) page.selectOption(webElement, SelectOption().setLabel(value)) else fail(ex.message)
        }
    }

    /**
     * selectByIndex usando retry igual a true, seleciona o Index em um combobox.
     * em caso de retry dar um sleep de 5s
     * @property webElement css or xpath.
     * @property retry true or false, default is false.
     */
    fun selectByIndex(
        webElement: String,
        indexValue: Int,
        retry: Boolean = true,
    ) {
        try {
            waitForSelector(webElement, stateATTACHED)
            page.press(webElement, "Enter")
            page.selectOption(webElement, SelectOption().setIndex(indexValue))
            println("Index selecionar: $indexValue")
        } catch (ex: Exception) {
            println("Error na primeira tentativa de selecionar o combobox: $webElement")
            sleep()
            dblclick(webElement)
            if (retry) page.selectOption(webElement, SelectOption().setIndex(indexValue)) else fail(ex.message)
        }
    }

    /**
     * dblclick funcao realiza o click duplo no elemento.
     * em caso de erro retorna true ou false.
     * @property webElement css or xpath.
     */
    fun dblclick(webElement: String): Boolean {
        try {
            page.dblclick(webElement)
            return true
        } catch (ex: Exception) {
            println("Error ao realizar o doble click")
            sleep()
            return false
        }
    }

    /**
     * scrollIntoView funcao realiza o scroll ate o elemento ou seja coloca o foco sobre o elemento.
     * @property scrollIntoView css or xpath.
     */
    fun scrollIntoView(webElement: String) {
        try {
            val element = page.querySelector(webElement)
            element.scrollIntoViewIfNeeded()
            sleep(2)
        } catch (ex: Exception) {
            println("A função scroll falhou...")
        }
    }

    /**
     * waitForSelector usando retry igual a true, primeiro verifica se esta visivel e no retry Attached.
     * @property webElemento css or xpath.
     * @property stateDOM stateVISIBLE, stateATTACHED or stateDETACHED, default is stateATTACHED.
     */
    fun waitForSelector(
        webElemento: String,
        stateDOM: Page.WaitForSelectorOptions = stateATTACHED,
    ) {
        try {
            loadPage()
            page.waitForSelector(webElemento, stateDOM)
            scrollIntoView(webElemento)
        } catch (ex: Exception) {
            println(ex.message)
            sleep()
            page.waitForSelector(webElemento, stateDOM)
        }
        loadPage()
    }
}
