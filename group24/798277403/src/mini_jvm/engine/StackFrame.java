package mini_jvm.engine;

import mini_jvm.cmd.ByteCodeCommand;
import mini_jvm.method.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * 函数帧
 * 包含操作数栈，返回值，局部变量表，常量池引用
 */
public class StackFrame {
	
	private List<JavaObject> localVariableTable = new ArrayList<JavaObject>();
	private Stack<JavaObject> oprandStack = new Stack<JavaObject>();
	
	int index = 0;
	
	private Method m = null;
	
	private StackFrame callerFrame = null;
	
	public StackFrame getCallerFrame() {
		return callerFrame;
	}

	public void setCallerFrame(StackFrame callerFrame) {
		this.callerFrame = callerFrame;
	}

	
	
	
	public static  StackFrame create(Method m){
		
		StackFrame frame = new StackFrame( m );			
		
		return frame;
	}

	
	private StackFrame(Method m) {		
		this.m = m;
		
	}
	
	
	
	public JavaObject getLocalVariableValue(int index){
		return this.localVariableTable.get(index);
	}
	
	public Stack<JavaObject> getOprandStack(){
		return this.oprandStack;
	}
	
	public int getNextCommandIndex(int offset){
		ByteCodeCommand[] cmds = m.getCodeAttr().getCmds();
		for(int i=0;i<cmds.length; i++){
			if(cmds[i].getOffset() == offset){
				return i;
			}
		}
		throw new RuntimeException("Can't find next command");
	}
	
	public ExecutionResult execute(){

		ByteCodeCommand [] cmds = m.getCmds();

		//执行字节码指令
		while(index<cmds.length){

			ExecutionResult result = new ExecutionResult();
			//缺省值是执行下一条命令
			result.setNextAction(ExecutionResult.RUN_NEXT_CMD);

			System.out.println(cmds[index].toString());

			//执行字节码指令
			cmds[index].execute(this,result);

			if(result.isRunNextCmd()){
				//执行完一条再执行下面一条
				index++;
			}
			else if(result.isExitCurrentFrame()){
				//退出当前栈帧
				return result;
			}
			else if(result.isPauseAndRunNewFrame()){
				//暂停当前栈帧的执行， 创建新的栈帧
				index++;
				return result;
			}
			else if(result.isJump()){
				//跳转到另外一行指令去执行
				int offset = result.getNextCmdOffset();
				this.index = getNextCommandIndex(offset);
			} else{
				index++;
			}

		}

		//当前StackFrmae的指令全部执行完毕，可以退出了
		ExecutionResult result = new ExecutionResult();
		result.setNextAction(ExecutionResult.EXIT_CURRENT_FRAME);
		return result;
	}

	
	
	
	public void setLocalVariableTable(List<JavaObject> values){
		this.localVariableTable = values;	
	}
	
	public void setLocalVariableValue(int index, JavaObject jo){
		//问题： 为什么要这么做？？
		if(this.localVariableTable.size()-1 < index){
			for(int i=this.localVariableTable.size(); i<=index; i++){
				this.localVariableTable.add(null);
			}
		}
		this.localVariableTable.set(index, jo);
		
		
	}
	
	public Method getMethod(){
		return m;
	}
	

}
