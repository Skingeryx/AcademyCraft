/**
* Copyright (c) Lambda Innovation, 2013-2016
* This file is part of the AcademyCraft mod.
* https://github.com/LambdaInnovation/AcademyCraft
* Licensed under GPLv3, see project root for more information.
*/
package cn.academy.energy.api;

import cn.academy.energy.api.block.IWirelessNode;
import cn.academy.support.EnergyBlockHelper;
import cn.academy.support.EnergyBlockHelper.IEnergyBlockManager;
import cn.lambdalib.annoreg.core.Registrant;
import net.minecraft.tileentity.TileEntity;

/**
 * @author WeAthFolD
 */
@Registrant
public class IFNodeManager implements IEnergyBlockManager {
    
    public static IFNodeManager instance = new IFNodeManager();
    
    private IFNodeManager() {
        EnergyBlockHelper.register(this);
    }

    @Override
    public boolean isSupported(TileEntity tile) {
        return tile instanceof IWirelessNode;
    }

    @Override
    public double getEnergy(TileEntity tile) {
        return ((IWirelessNode)tile).getEnergy();
    }

    @Override
    public void setEnergy(TileEntity tile, double energy) {
        ((IWirelessNode)tile).setEnergy(energy);
    }

    @Override
    public double charge(TileEntity tile, double amt, boolean ignoreBandwidth) {
        IWirelessNode node = ((IWirelessNode)tile);
        double max = node.getMaxEnergy() - node.getEnergy();
        double chg = Math.min(amt, max);
        if(!ignoreBandwidth)
            chg = Math.min(node.getBandwidth(), chg);
        
        node.setEnergy(node.getEnergy() + chg);
        return amt - chg;
    }

    @Override
    public double pull(TileEntity tile, double amt, boolean ignoreBandwidth) {
        IWirelessNode node = (IWirelessNode) tile;
        double max = node.getEnergy();
        double pull = Math.min(max, amt);
        if(!ignoreBandwidth)
            pull = Math.min(node.getBandwidth(), pull);
        
        node.setEnergy(node.getEnergy() - pull);
        return pull;
    }

}
