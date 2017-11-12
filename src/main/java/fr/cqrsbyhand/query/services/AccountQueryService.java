package fr.cqrsbyhand.query.services;

import fr.cqrsbyhand.query.models.AccountView;

public interface AccountQueryService {
  AccountView getAccount(String accountId);
}
