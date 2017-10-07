/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nn.optim.kernels;

import com.aparapi.Kernel;
import com.aparapi.Range;

/**
 *
 * @author bowen
 */
public class SGDKernel extends Kernel {
    
    private float[] weights = new float[0];
    private float[] gradients = new float[0];
    
    private final float[] prop = new float[2];
    
    public void call(float[] weights, float[] gradients, float learningRate, float clip) {
        this.weights = weights;
        this.gradients = gradients;
        prop[0] = learningRate;
        prop[1] = clip;
        
        Range range = Range.create(weights.length);
        execute(range);
    }
    
    @Override
    public void run() {
        int i = getGlobalId();
        
        float gradient = min(max(gradients[i], -prop[1]), prop[1]);
        
        weights[i] = weights[i] + (prop[0] * gradient);
    }
    
}
