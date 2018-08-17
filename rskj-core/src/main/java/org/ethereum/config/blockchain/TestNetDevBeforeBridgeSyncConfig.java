/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 * (derived from ethereumJ library, Copyright (c) 2016 <ether.camp>)
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

package org.ethereum.config.blockchain;

import co.rsk.config.BridgeConstants;
import co.rsk.config.BridgeTestNetConstants;
import co.rsk.core.BlockDifficulty;
import org.ethereum.config.Constants;
import org.ethereum.core.BlockHeader;

import java.math.BigInteger;


public class TestNetDevBeforeBridgeSyncConfig extends GenesisConfig {


    public static class TestNetDevConstants extends GenesisConstants {
    	
        private static final byte CHAIN_ID = 91;
        private final BlockDifficulty minimumDifficulty = new BlockDifficulty(BigInteger.valueOf(1));

        @Override
        public BlockDifficulty getFallbackMiningDifficulty() { return BlockDifficulty.ZERO; }
        
        @Override
        public BridgeConstants getBridgeConstants() {
            return BridgeTestNetConstants.getInstance();
        }

        @Override
        public BlockDifficulty getMinimumDifficulty() {
            return minimumDifficulty;
        }

        @Override
        public int getDurationLimit() {
            return 10;
        }

        @Override
        public int getNewBlockMaxSecondsInTheFuture() {
            return 540;
        }

        @Override
        public byte getChainId() {
            return TestNetDevConstants.CHAIN_ID;
        }
        

    }

    public TestNetDevBeforeBridgeSyncConfig() {
        super(new TestNetDevConstants());
    }

    protected TestNetDevBeforeBridgeSyncConfig(Constants constants) {
        super(constants);
    }


    @Override
    public BlockDifficulty calcDifficulty(BlockHeader curBlock, BlockHeader parent) {
        // Always the minimun difficulty
    	return getConstants().getMinimumDifficulty();
    }
    
    @Override
    public boolean areBridgeTxsFree() {
        return true;
    }
}
