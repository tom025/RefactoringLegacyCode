package pull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.BalancesOfMonth;
import common.BalancesOfMonthCalculator;
import common.Transaction;

public class PullingBalancesCalculator implements BalancesOfMonthCalculator
{

	private final List<Transaction> transactions;

	public PullingBalancesCalculator(List<Transaction> transactions)
	{
		super();
		this.transactions = transactions;
	}

	@Override
	public void fillData(List<BalancesOfMonth> balancesOfMonthList)
	{
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth();

		for (BalancesOfMonth balancesOfMonth : balancesOfMonthList)
		{
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction> transactionsOfMonth = transactionsOfMonth(dateOfMonth);

			int precedingBalance = valuesOfMonth.getBalance();

			valuesOfMonth = new ValuesOfMonth(dateOfMonth, transactionsOfMonth, precedingBalance);

			balancesOfMonth.setBalance(valuesOfMonth.getBalance());
			balancesOfMonth.setAverageBalance(valuesOfMonth.getAverageBalance());
		}
	}

	private List<Transaction> transactionsOfMonth(LocalDate date)
	{
		List<Transaction> results = new ArrayList<Transaction>();
		for (Transaction transaction : transactions)
		{
			LocalDate dateOfTransaction = transaction.getDate();
			if (areSameMonthAndYear(date, dateOfTransaction))
			{
				results.add(transaction);
			}
		}
		return results;
	}

	private boolean areSameMonthAndYear(LocalDate date, LocalDate dateOfTransaction)
	{
		return dateOfTransaction.getMonthOfYear() == date.getMonthOfYear() && dateOfTransaction.getYear() == date.getYear();
	}

}
