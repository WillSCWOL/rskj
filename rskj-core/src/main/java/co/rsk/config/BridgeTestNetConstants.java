/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package co.rsk.config;

import co.rsk.bitcoinj.core.BtcECKey;
import co.rsk.bitcoinj.core.Coin;
import co.rsk.bitcoinj.core.NetworkParameters;
import co.rsk.peg.AddressBasedAuthorizer;
import co.rsk.peg.Federation;
import com.google.common.collect.Lists;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeTestNetConstants extends BridgeConstants {
    private static BridgeTestNetConstants instance = new BridgeTestNetConstants();

    BridgeTestNetConstants() {
        btcParamsString = NetworkParameters.ID_TESTNET;

        //PubKeys of Kcoin Addresses that conform the federation( the multisig of these conform the deferation address)
         BtcECKey federator0PublicKey = BtcECKey.fromPublicOnly(Hex.decode("03ddf489d2e3f762da95ebc870936c2f9d77322210fa1466fa9680a52cf09afd57"));
      
        List<BtcECKey> genesisFederationPublicKeys = Lists.newArrayList(
                federator0PublicKey
        );

        // Currently set to:
        // Wednesday, January 3, 2018 12:00:00 AM GMT-03:00
        Instant genesisFederationAddressCreatedAt = Instant.ofEpochMilli(1514948400l);

        genesisFederation = new Federation(
                genesisFederationPublicKeys,
                genesisFederationAddressCreatedAt,
                1L,
                getBtcParams()
        );

        btc2RskMinimumAcceptableConfirmations = 10;
        btc2RskMinimumAcceptableConfirmationsOnRsk = 10;
        rsk2BtcMinimumAcceptableConfirmations = 10;

        updateBridgeExecutionPeriod = 1 * 30 * 1000; // 30 seconds

        maxBtcHeadersPerRskBlock = 500;

        minimumLockTxValue = Coin.valueOf(1000000);
        minimumReleaseTxValue = Coin.valueOf(500000);

        // Passphrases are kept private
        //PubKeys of RSK addresses that are authorized to change the federation. It is checked in the bridge methods that
        //alter the federation in the "from:" param of the rsk transaction
        List<ECKey> federationChangeAuthorizedKeys = Arrays.stream(new String[]{
            "04ddf489d2e3f762da95ebc870936c2f9d77322210fa1466fa9680a52cf09afd5771f66de93f82e734349c955fb8ca2cc956b9d098819ff7100b5b10b6a3d5be71"
        }).map(hex -> ECKey.fromPublicOnly(Hex.decode(hex))).collect(Collectors.toList());

        federationChangeAuthorizer = new AddressBasedAuthorizer(
                federationChangeAuthorizedKeys,
                AddressBasedAuthorizer.MinimumRequiredCalculation.MAJORITY
        );

        // Passphrases are kept private
        //PubKeys of RSK addresses that are authorized to add/remove the bridge lock whitelist. It is checked in the bridge methods that
        //alter the lock whitelist in the "from:" param of the rsk transaction
        List<ECKey> lockWhitelistAuthorizedKeys = Arrays.stream(new String[]{
        	 "04ddf489d2e3f762da95ebc870936c2f9d77322210fa1466fa9680a52cf09afd5771f66de93f82e734349c955fb8ca2cc956b9d098819ff7100b5b10b6a3d5be71"
        }).map(hex -> ECKey.fromPublicOnly(Hex.decode(hex))).collect(Collectors.toList());

        lockWhitelistChangeAuthorizer = new AddressBasedAuthorizer(
                lockWhitelistAuthorizedKeys,
                AddressBasedAuthorizer.MinimumRequiredCalculation.ONE
        );

        // //ORIGINAL
//        federationActivationAge = 60L;
//        fundsMigrationAgeSinceActivationBegin = 60L;
//        fundsMigrationAgeSinceActivationEnd = 900L;

        //FOR TESTING
        federationActivationAge = 10L;
        fundsMigrationAgeSinceActivationBegin = 10L;
        fundsMigrationAgeSinceActivationEnd = 20L;

        //PubKeys of RSK addresses that are authorized to change the bridge feePerKb. It is checked in the bridge methods that
        //alter the feePerKb in the "from:" param of the rsk transaction
        List<ECKey> feePerKbAuthorizedKeys = Arrays.stream(new String[]{
        	 "04ddf489d2e3f762da95ebc870936c2f9d77322210fa1466fa9680a52cf09afd5771f66de93f82e734349c955fb8ca2cc956b9d098819ff7100b5b10b6a3d5be71"
        }).map(hex -> ECKey.fromPublicOnly(Hex.decode(hex))).collect(Collectors.toList());

        feePerKbChangeAuthorizer = new AddressBasedAuthorizer(
                feePerKbAuthorizedKeys,
                AddressBasedAuthorizer.MinimumRequiredCalculation.MAJORITY
        );

        genesisFeePerKb = Coin.MILLICOIN;
    }

    public static BridgeTestNetConstants getInstance() {
        return instance;
    }

}
