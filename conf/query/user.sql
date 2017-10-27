include "common.sql.conf"

insert: """
insert into user
  ( name
  , legal_name
  , email
  , password
  , status
  , created
  , first_login
  , last_login
  , password_changed
  , failed_attempts
  )
values
  ( {name}
  , {legalName}
  , {email}
  , {password}
  , {status}
  , {created}
  , {firstLogin}
  , {lastLogin}
  , {passwordChanged}
  , {failedAttempts}
  )
"""



update: """
update user set
    name = {name}
  , legal_name = {legalName}
  , email = {email}
  , password = {password}
  , status = {status}
  , first_login = {firstLogin}
  , last_login = {lastLogin}
  , password_changed = {passwordChanged}
  , failed_attempts = {failedAttempts}
where
  id = {id}
"""



delete: """
delete from user
where
  id = {id}
"""



select: """
select * from user
"""



get: ${select}"""
where
  id = {id}
"""



byName: ${select}"""
where
  name """${unicode}""" = {name}
order by
  name
"""



byNameLike: ${select}"""
where
  name """${unicode}""" like {name}
order by
  name
"""



byLegalName: ${select}"""
where
  legal_name """${unicode}""" = {legalName}
order by
  legal_name
"""



byLegalNameLike: ${select}"""
where
  legal_name """${unicode}""" like {legalName}
order by
  legal_name
"""



byEmail: ${select}"""
where
  email = {email}
"""
