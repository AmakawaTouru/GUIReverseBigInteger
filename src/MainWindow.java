import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class MainWindow {

	protected Shell shell;
	private Text inputNumber;
	private Text result;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(10, 27, 76, 20);
		label.setText("\u8BF7\u8F93\u5165\uFF1A");
		
		inputNumber = new Text(shell, SWT.BORDER);
		inputNumber.setBounds(121, 27, 269, 26);
		
		Button reverse = new Button(shell, SWT.NONE);
		reverse.addMouseListener(new MouseAdapter() {
			private String input;
			private boolean sign;

			public void mouseDoubleClick(MouseEvent e) {
				input = inputNumber.getText();
				if(!input.matches("^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$")) {
					result.setText("������Ĳ���һ��ʮ������������������!");}
				else{
					//ȥ��ǰ��0
					BigDecimal inPutNumber = new BigDecimal(input);
					//��ȥ��0
					inPutNumber = inPutNumber.stripTrailingZeros();
					//��ѧ���ʽת��Ϊ�����ı��
					String sample = inPutNumber.toPlainString();
					//�������ж�
					if(sample.charAt(0) == '-') {
						sign = true;
						sample = sample.substring(1,sample.length());
					}else {
						sign = false;
					}
					//����.�����и�
					String[] results = sample.split("\\.");
					//�����鳤��Ϊ1ʱ��˵��ֻ���������֣�Ϊ2��˵��ΪС��
					if(results.length == 1) {
						String res = reverse(results[0]);
						//ȥ��ǰ��0
						res = res.replaceAll("^(0+)", "");
						result.setText(useSign(res));
					}else if(results.length == 2) {
						//���߶��ȷ�ת
						String partOfInteger = reverse(results[0]);
						String partOfDecimal = reverse(results[1]);
						
						//��������ͬ��,ȥ��ǰ��0
						partOfInteger = partOfInteger.replaceAll("^(0+)", "");
						
						//С�����֣���������ǰ��0������Ҫȥ����0
						partOfDecimal = partOfDecimal.replaceAll("(0)+$", "");
						
						//���ƴ��
						result.setText(useSign(partOfInteger+"."+partOfDecimal));
					}
				}
			}
			
			private String reverse(String str) {
				StringBuilder sb = new StringBuilder(str);
				sb = sb.reverse();
				return sb.toString();
			}
			
			private String useSign(String res) {
				if(sign) {
					return "-"+res;
				}
				return res;
			}
			

			private BigInteger reverse(BigInteger x) {
				BigInteger result = new BigInteger("0");
				BigInteger zero = new BigInteger("0");
				BigInteger remainder = new BigInteger("0");
				BigInteger ten = new BigInteger("10");
				while (!x.equals(zero)) {
					remainder = x.remainder(ten);
					result = result.multiply(ten).add(remainder);
					x = x.divide(ten);
				}
				return result;
			}
			
			
		});
		reverse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
		reverse.setBounds(126, 88, 98, 30);
		reverse.setText("\u8F6C\u6362");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(10, 149, 76, 20);
		label_1.setText("\u7ED3\u679C\uFF1A");
		
		result = new Text(shell, SWT.BORDER);
		result.setBounds(121, 143, 269, 26);
		
		Button reset = new Button(shell, SWT.NONE);
		reset.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				inputNumber.setText("");
				result.setText("");
			}
		});
		reset.setText("\u6E05\u7A7A");
		reset.setBounds(266, 88, 98, 30);

	}
}
