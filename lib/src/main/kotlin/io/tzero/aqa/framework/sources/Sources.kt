package io.tzero.aqa.framework.sources

import io.tzero.aqa.framework.sources.Data.invalidIds
import io.tzero.aqa.framework.sources.Data.spaceAndNonPrinted
import org.junit.jupiter.params.provider.ArgumentsProvider

class SpaceAndNonPrintedSource : ArgumentsProvider by stream(args = spaceAndNonPrinted)

class InvalidIdsSource : ArgumentsProvider by stream(args = invalidIds)
