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

public class BridgeMainNetConstants extends BridgeConstants {
    private static BridgeMainNetConstants instance = new BridgeMainNetConstants();

    BridgeMainNetConstants() {
        btcParamsString = NetworkParameters.ID_MAINNET;

        //PubKeys of Kcoin Addresses that conform the federation( the multisig of these conform the deferation address)
        BtcECKey federator0PublicKey = BtcECKey.fromPublicOnly(Hex.decode("02e6c05b5192c69e0280906d50c7a99cef492287882ace686ad37e666fb3a694cd"));
     
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

        //THESE ARE THE REAL VALUES
        btc2RskMinimumAcceptableConfirmations = 100;
        btc2RskMinimumAcceptableConfirmationsOnRsk = 200;
        rsk2BtcMinimumAcceptableConfirmations = 200;

        updateBridgeExecutionPeriod = 3 * 60 * 1000; // 3 minutes

        maxBtcHeadersPerRskBlock = 1001;

        minimumLockTxValue = Coin.valueOf(1000000);
        minimumReleaseTxValue = Coin.valueOf(800000);
        
        //THESE ARE THE TEST VALUES
//        btc2RskMinimumAcceptableConfirmations = 10;
//        btc2RskMinimumAcceptableConfirmationsOnRsk = 10;
//        rsk2BtcMinimumAcceptableConfirmations = 10;
//
//        updateBridgeExecutionPeriod = 1 * 30 * 1000; // 30 seconds
//
//        maxBtcHeadersPerRskBlock = 500;
//
//        minimumLockTxValue = Coin.valueOf(1000000);
//        minimumReleaseTxValue = Coin.valueOf(500000);

        // Passphrases are kept private
        //PubKeys of RSK addresses that are authorized to change the federation. It is checked in the bridge methods that
        //alter the federation in the "from:" param of the rsk transaction
        List<ECKey> federationChangeAuthorizedKeys = Arrays.stream(new String[]{
            "04dbad65de5a49e14ba4ea9752085c99c0bd6559ea6fcdaab8b774845f52afe870f2ffe3ba14c9ac7d54b0766054e6b296f79791633ffee861eb652a6469a14b4a",
            "04543eca9ad50981c60a9c1482581724f62c26dc77122bdc2628bcce0d7c5476bc62a4e992b79867e6e700760da7545827e6da581a255fa2c3695ce9c3fa6b0703",
            "04144484684ecdb56d37162d1142840b12c2023014e535b54be6b54307e17937b0bd4d5201627ecd5457cc32dbe7f3f58669168a3c37c0d23119b8b3a2716a3b68"
        }).map(hex -> ECKey.fromPublicOnly(Hex.decode(hex))).collect(Collectors.toList());

        federationChangeAuthorizer = new AddressBasedAuthorizer(
                federationChangeAuthorizedKeys,
                AddressBasedAuthorizer.MinimumRequiredCalculation.MAJORITY
        );

        // Passphrases are kept private
        //PubKeys of RSK addresses that are authorized to add/remove the bridge lock whitelist. It is checked in the bridge methods that
        //alter the lock whitelist in the "from:" param of the rsk transaction
        List<ECKey> lockWhitelistAuthorizedKeys = Arrays.stream(new String[]{
        	 "04dbad65de5a49e14ba4ea9752085c99c0bd6559ea6fcdaab8b774845f52afe870f2ffe3ba14c9ac7d54b0766054e6b296f79791633ffee861eb652a6469a14b4a",
             "04543eca9ad50981c60a9c1482581724f62c26dc77122bdc2628bcce0d7c5476bc62a4e992b79867e6e700760da7545827e6da581a255fa2c3695ce9c3fa6b0703",
             "04144484684ecdb56d37162d1142840b12c2023014e535b54be6b54307e17937b0bd4d5201627ecd5457cc32dbe7f3f58669168a3c37c0d23119b8b3a2716a3b68"
        }).map(hex -> ECKey.fromPublicOnly(Hex.decode(hex))).collect(Collectors.toList());

        lockWhitelistChangeAuthorizer = new AddressBasedAuthorizer(
                lockWhitelistAuthorizedKeys,
                AddressBasedAuthorizer.MinimumRequiredCalculation.ONE
        );
        
        //THESE ARE THE REAL VALUES
        federationActivationAge = 18500L;

        fundsMigrationAgeSinceActivationBegin = 0L;
        fundsMigrationAgeSinceActivationEnd = 10585L;
        
       //THESE ARE THE TEST VALUES
//        federationActivationAge = 10L;
//        fundsMigrationAgeSinceActivationBegin = 10L;
//        fundsMigrationAgeSinceActivationEnd = 20L;

        //PubKeys of RSK addresses that are authorized to change the bridge feePerKb. It is checked in the bridge methods that
        //alter the feePerKb in the "from:" param of the rsk transaction
        List<ECKey> feePerKbAuthorizedKeys = Arrays.stream(new String[]{
        	 "04dbad65de5a49e14ba4ea9752085c99c0bd6559ea6fcdaab8b774845f52afe870f2ffe3ba14c9ac7d54b0766054e6b296f79791633ffee861eb652a6469a14b4a",
             "04543eca9ad50981c60a9c1482581724f62c26dc77122bdc2628bcce0d7c5476bc62a4e992b79867e6e700760da7545827e6da581a255fa2c3695ce9c3fa6b0703",
             "04144484684ecdb56d37162d1142840b12c2023014e535b54be6b54307e17937b0bd4d5201627ecd5457cc32dbe7f3f58669168a3c37c0d23119b8b3a2716a3b68"
        }).map(hex -> ECKey.fromPublicOnly(Hex.decode(hex))).collect(Collectors.toList());

        feePerKbChangeAuthorizer = new AddressBasedAuthorizer(
                feePerKbAuthorizedKeys,
                AddressBasedAuthorizer.MinimumRequiredCalculation.MAJORITY
        );

        genesisFeePerKb = Coin.MILLICOIN.multiply(5);
    }

    public static BridgeMainNetConstants getInstance() {
        return instance;
    }

}
