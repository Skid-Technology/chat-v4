/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiKeyTypedListener;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroInputComponent;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;

class FrameMacrosAddMacroKeyTypedListener
implements GuiKeyTypedListener {
    final FrameMacros var_gg_vape_ui_click_frame_impl_FrameMacros_C;
    final FrameMacrosAddMacroInputComponent var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d;

    FrameMacrosAddMacroKeyTypedListener(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent, FrameMacros frameMacros) {
        this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d = frameMacrosAddMacroInputComponent;
        this.var_gg_vape_ui_click_frame_impl_FrameMacros_C = frameMacros;
    }

    @Override
    public void onKeyTyped(char c, int n) {
        if (FrameMacrosAddMacroInputComponent.p$src$Z$xrofzd(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d)) {
            this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d.submit();
            if (FrameMacrosAddMacroInputComponent.j(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d) != null) {
                Macro macro = Macro.create(FrameMacrosAddMacroInputComponent.j(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d));
                if (Vape.INSTANCE.getMacrosManager().getMacro(FrameMacrosAddMacroInputComponent.j(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d)) != null) {
                    this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d.setText("");
                    ClientSettings.activeComponent = null;
                    return;
                }
                ClientSettings.activeComponent = null;
                FrameMacrosAddMacroInputComponent.w(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d, new FrameMacrosEditor(this.var_gg_vape_ui_click_frame_impl_FrameMacros_C, macro));
                FrameMacrosAddMacroInputComponent.p(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d).N$src$V$13y6z98();
                this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d.addChildren(FrameMacrosAddMacroInputComponent.p(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d));
            }
            this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_d.setText("");
        }
    }
}

