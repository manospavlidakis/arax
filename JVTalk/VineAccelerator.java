package Vinetalk;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class VineAccelerator extends VineObject
{
	public enum Type {
		ANY(0),       /**< Let Scheduler Decide */
		GPU(1),       /**< Run on GPU with CUDA */
		GPU_SOFT(2),  /**< Run on CPU with software CUDA(Useful for debug?) */
		CPU(3),       /**< Run Native x86 code */
		FPGA(4),      /**< Custom Fpga accelerator */
		NANO_ARM(5),  /**< ARM accelerator core from NanoStream */
		NANO_CORE(6), /**< NanoStreams FPGA accelerator */
		VINE_ACCEL_TYPES(7); /** End Marker */
		private final int value;

		Type(int value)
		{this.value = value;}
		int getAsInt()
		{return value;}
	}

	public VineAccelerator(Pointer ptr)
	{
		super(ptr);
	}

	public void issue(VineTask task)
	{
		VineBuffer[] in = task.getInputs();
		VineBuffer[] out = task.getOutputs();
		Pointer args = (task.getArgs()!=null)?task.getArgs().getPointer():null;
		task.setTask(VineTalkInterface.INSTANCE.vine_task_issue(getPointer(),task.getProcedure(),args,in.length,in,out.length,out));
	}

	public void release()
	{
		PointerByReference ptr_ref = new PointerByReference(getPointer());
		VineTalkInterface.INSTANCE.vine_accel_release(ptr_ref);
	}
}