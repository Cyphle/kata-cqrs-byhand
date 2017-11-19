package fr.cqrsbyhand.query.services;

import fr.cqrsbyhand.query.models.AccountView;

public interface AccountQuery {
  AccountView getAccountByName(String accountName);
}
